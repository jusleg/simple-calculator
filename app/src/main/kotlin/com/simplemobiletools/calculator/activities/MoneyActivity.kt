package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import me.grantland.widget.AutofitHelper

/* ktlint-disable no-wildcard-imports */
import java.util.*
import android.location.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_money.*
import com.simplemobiletools.commons.extensions.*
import com.simpletools.calculator.commons.operations.TaxOperation

/* ktlint-enable no-wildcard-imports */

class MoneyActivity : SimpleActivity(), Calculator, LocationListener {

    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false
    private var taxDialog: AlertDialog.Builder? = null
    private var custom_dialog: AlertDialog? = null
    private var conversionDialog: AlertDialog? = null
    private var locationManager: LocationManager? = null
    private var GPS_REQUEST_CODE = 101
    private var MINIMUM_TIME_BETWEEN_UPDATES: Long = 300000L //in ms
    private var MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Float = 1000f //in meters
    private var currentLongitude: Double = 0.0
    private var currentLatitude: Double = 0.0
    private var lastLocation: Location = Location(LocationManager.GPS_PROVIDER)
    private var geocoder: Geocoder? = null
    private var province: String = ""
    private var testing: Boolean = false

    lateinit var calc: MoneyCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        calc = MoneyCalculatorImpl(this, applicationContext)
        updateViewColors(money_holder, config.textColor)
        updateButtonColor(config.customPrimaryColor)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //Checks for permission granted for location service
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if not granted then request permission
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), GPS_REQUEST_CODE)
        }
        try {
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this)
        } catch (e: SecurityException) {
            print("NO PERMISSION GRANTED!")
        }

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_currency.setOnClickListener { displayConversionDialog() }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_tip.setOnClickListener { displayTipsDialog() }
        result.setOnLongClickListener { copyToClipboard(result.value); true }
        btn_taxes.setOnClickListener({ view ->
            var gpsStatus: Boolean = false
            try {
                //checks gps status
                gpsStatus = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (e: Exception) {}
            //if permission not granted then set gpsStatus to false
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                gpsStatus = false
            }

            //if the gps status is enabled then get the location
            if (gpsStatus) {
                //get the last known location
                lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                //sets the latitude and longitude to that of last location
                if (lastLocation != null) {
                    currentLatitude = lastLocation.latitude
                    currentLongitude = lastLocation.longitude
                } }
            geocoder = Geocoder(applicationContext, Locale.getDefault())
            taxLocationStrat(gpsStatus, currentLatitude, currentLongitude, geocoder!!, testing)
        })

        AutofitHelper.create(result)
    }

    override fun onLocationChanged(location: Location) {
        currentLatitude = location.latitude
        currentLongitude = location.longitude
    }

    override fun onStatusChanged(s: String, i: Int, b: Bundle) {}

    override fun onProviderDisabled(s: String) {
        Toast.makeText(applicationContext, "Location service has been turned off", Toast.LENGTH_LONG).show()
    }
    override fun onProviderEnabled(s: String) {
        Toast.makeText(applicationContext, "Locattion service has been turned on", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GPS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("PermissionTag", "Permission Granted (if 0 then is granted): " + grantResults[0])
            }
        }
    }

    //method signature here has been added for the sake of testing
    fun taxLocationStrat(gpsEnabled: Boolean, latitude: Double, longitude: Double, geocoder: Geocoder, testing: Boolean) {
        //if location service is enabled then retrieve the latest location and performs tax rate with that location
        if (gpsEnabled) {
            if (testing == true) {
                calc.performTaxing(province)
            } else {
                //uses Android's Geocoder to locate the address with a given latitude and longitude
                var addresses: List<Address>? = null
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    //gets the province
                    province = addresses!!.get(0).adminArea
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Unable to recognize your location, please choose one of the following provinces", Toast.LENGTH_SHORT).show()
                    spawnTaxModal()
                    return
                }

                //if province is empty if country is not Canada then prompt to choose a Canadian province
                if (province == "" || addresses!!.get(0).countryName != "Canada") {
                    Toast.makeText(applicationContext, "Unable to recognize your location, please choose one of the following provinces", Toast.LENGTH_SHORT).show()
                    spawnTaxModal()
                    return
                }
                var taxRate: Double = (TaxOperation.getTaxRate(province)) * 100
                var message: String = "Current Location: " + province + "\nTax rate: " + taxRate + "% "
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

                calc.performTaxing(province)
            }
            //if location service is disabled then let the user select manually the province
        } else {
            Toast.makeText(applicationContext, "Location services not enabled", Toast.LENGTH_SHORT).show()
            spawnTaxModal()
            return
        }
    }

    //this method is only used for testing purpose. NEVER CALL THIS METHOD UNLESS FOR TESTING
    fun supersedeProvince(province: String) {
        this.province = province
    }

    fun spawnTaxModal() {
        taxDialog = AlertDialog.Builder(this)
        val taxDialogView = layoutInflater.inflate(R.layout.tax_modal, null)
        taxDialog!!.setView(taxDialogView)
        taxDialog!!.setCancelable(true)
        custom_dialog = taxDialog!!.create()
        custom_dialog?.show()

        custom_dialog!!.findViewById<ListView>(R.id.province_selector_tax).setOnItemClickListener {
            adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            var location = custom_dialog!!.findViewById<ListView>(R.id.province_selector_tax).getItemAtPosition(i).toString()
            custom_dialog?.dismiss()
            calc.performTaxing(location)
        }
    }

    fun getTaxDialog(): AlertDialog? {
        return custom_dialog
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            restartActivity()
            return
        }

        if (storedTextColor != config.textColor) {
            updateViewColors(money_holder, config.textColor)
            updateButtonColor(config.customPrimaryColor)
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
    }

    override fun setValue(value: String) {
        result.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun setClear(text: String) {}

    override fun getFormula(): String { return "" }

    override fun setFormula(value: String) {}

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun updateButtonColor(color: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(config.primaryColor)
        val myList = ColorStateList(states, colors)

        getMoneyButtonIds().forEach {
            it.setTextColor(getContrastColor(color))
            ViewCompat.setBackgroundTintList(it, myList)
        }
    }

    @SuppressLint("Range")
    private fun getContrastColor(color: Int): Int {
        val DARK_GREY = -13421773
        val y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000
        return if (y >= 149) DARK_GREY else Color.WHITE
    }

    private fun getMoneyButtonIds() = arrayOf(btn_tip, btn_currency, btn_taxes)

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun displayTipsDialog() {
        val tipAmount = arrayOf("10%", "12%", "15%", "18%", "20%", "25%")

        val tipDialog = AlertDialog.Builder(this@MoneyActivity)
        tipDialog.setTitle("Calculate Tip")

        tipDialog.setItems(tipAmount, DialogInterface.OnClickListener { _, index ->
            var tipPercentage = tipAmount[index].trimEnd('%').toDouble() / 100
            calc.calculateTip(tipPercentage)
        })

        tipDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            // User cancelled the dialog
        })
        tipDialog.show()
    }

    private fun displayConversionDialog() {
        val conversionDialogBuilder = AlertDialog.Builder(this)
        val conversionDialogView = layoutInflater.inflate(R.layout.conversion_modal, null)
        conversionDialogView.findViewById<Button>(R.id.btn_conversion).setOnClickListener {
            performConversion(conversionDialogView.findViewById<Spinner>(R.id.conversion_spinner1).selectedItem,
                    conversionDialogView.findViewById<Spinner>(R.id.conversion_spinner2).selectedItem)
            conversionDialog!!.dismiss()
        }
        conversionDialogBuilder!!.setView(conversionDialogView)
        conversionDialogBuilder!!.setCancelable(true)
        conversionDialog = conversionDialogBuilder!!.create()
        conversionDialog!!.show()
    }

    private fun performConversion(from: Any, to: Any) {
        if (!from.toString().equals(to.toString()) && isOnline()) {
            calc.calcCurrency(from.toString(), to.toString(), this)
        } else if (!isOnline()) {
            displayToast("It seems there has been a connection problem, contact you ISP for more details !")
        }
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun displayToast(message: String) {
        applicationContext.toast(message, 100)
    }
}
