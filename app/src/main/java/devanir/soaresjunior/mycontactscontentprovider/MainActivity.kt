package devanir.soaresjunior.mycontactscontentprovider

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var customAdapter = CustomAdapter()

    private var contactModelArrayList: ArrayList<ContactModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        contactModelArrayList = ArrayList()
        listView.adapter = customAdapter

        requestPermission()
//        listView!!.adapter = customAdapter

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (permission == PackageManager.PERMISSION_GRANTED) {

            setContacts()


        } else {


            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Contact permission is needed", Toast.LENGTH_SHORT).show()
            }

            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 1)

        }
    }

    private fun setContacts() {
        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"

        )
        /*This loop will go through all the contacts of android device.
            In every iteration, it will fetch name and phone number in different variables.
            Then, it will store both information in object of contactModel class and finally, object is stored in ArrayList.
            Then this Arraylist will populate the listview.*/
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactModel = ContactModel()
            contactModel.setNames(name)
            contactModel.setNumbers(phoneNumber)
            contactModelArrayList!!.add(contactModel)
            Log.d("name>>", name + "  " + phoneNumber)

        }
        customAdapter?.updateData(contactModelArrayList ?: emptyList<ContactModel>())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            1 -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setContacts()
                  // customAdapter?.notifyDataSetChanged()


                } else {
                    Toast.makeText(this, "something went wrong!", Toast.LENGTH_SHORT).show();
                }
                return
            }
        }

    }

}

