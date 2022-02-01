package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ManagePermissions (
    val activity: Activity,
    val permiso: String,
    val code: Int
    ) {
        fun checkPermissions(): Boolean {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    activity, permiso
                     ) != PackageManager.PERMISSION_GRANTED
                ) {

                // Si el usuario ya lo ha rechazado al menos una vez (TRUE),
                // se puede mostrar una explicación.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                 activity, permiso)
                ) {
                showAlert()
                } else {
                ActivityCompat.requestPermissions(
                     activity, arrayOf(permiso), code
                )
                }
                } else {
                }

            return ContextCompat.checkSelfPermission(
             activity, permiso
             ) == PackageManager.PERMISSION_GRANTED
            }

        // Función encargada de mostrar un AlertDialog con información adicional.
        private fun showAlert() {
            val builder = AlertDialog.Builder(activity)

            builder.setTitle("Permissions")
            builder.setMessage("Camera access needed to take ticket photo")

            builder.setPositiveButton(android.R.string.ok) { _, _ ->
             ActivityCompat.requestPermissions(
                 activity, arrayOf(permiso), code
             )
            }

           builder.setNeutralButton(android.R.string.cancel, null)
           builder.show()
        }
}