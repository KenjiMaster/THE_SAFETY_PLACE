import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import com.katespe.laprimera.primeraapp.Constants.REQUEST_ENABLE_BT
import java.io.IOException
import java.util.*

object BluetoothConnection {
    private var bluetoothSocket: BluetoothSocket? = null
    var isConnected: Boolean = false
    lateinit var address: String
    private val myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun connect(context: Context, address: String) {
        if (bluetoothSocket == null || !isConnected) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // Si no se tienen los permisos, solicitarlos
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_ENABLE_BT)
            }
            val device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address)
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
            bluetoothSocket?.connect()
            isConnected = true
        }
    }

    fun sendCommand(context: Context, input: String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen los permisos, solicitarlos
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_ENABLE_BT)
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // Se otorgaron los permisos, continuar con la operación
            try {
                bluetoothSocket?.outputStream?.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            // Los permisos fueron denegados por el usuario, manejar esta situación
            // Puedes mostrar un mensaje al usuario indicando que la operación no se puede realizar sin permisos de Bluetooth.
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
            isConnected = false
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getBluetoothSocket(): BluetoothSocket? {
        return bluetoothSocket
    }
    fun enviarValorBluetooth(valor: String) {
        val mensaje = valor.toString()
        Log.d("EnviarValor", "Valor a enviar: $mensaje")

        if (BluetoothConnection.getBluetoothSocket() != null) {
            try {
                Log.d("EnviarValor", "Antes de enviar mensaje")
                BluetoothConnection.getBluetoothSocket()?.outputStream?.write(mensaje.toByteArray())
                Log.d("EnviarValor", "Mensaje enviado: $mensaje")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}










