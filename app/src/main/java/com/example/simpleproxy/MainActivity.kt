package com.example.simpleproxy

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.text.InputType
import android.widget.*

class MainActivity : Activity() {
    private lateinit var host: EditText
    private lateinit var port: EditText
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(36, 48, 36, 24)
            setBackgroundColor(0xFF121212.toInt())
        }
        fun input(hintText: String, type: Int = InputType.TYPE_CLASS_TEXT) =
            EditText(this).apply {
                hint = hintText
                inputType = type
                setHintTextColor(0xFFAAAAAA.toInt())
                setTextColor(0xFFFFFFFF.toInt())
            }
        root.addView(TextView(this).apply {
            text = "Simple Proxy"
            textSize = 26f
            setTextColor(0xFFFFFFFF.toInt())
        })
        host = input("SOCKS5 host or IP")
        port = input("Port", InputType.TYPE_CLASS_NUMBER)
        user = input("Username (optional)")
        pass = input("Password (optional)", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        listOf(host, port, user, pass).forEach(root::addView)

        root.addView(Button(this).apply {
            text = "Connect (VPN scaffold)"
            setOnClickListener {
                if (host.text.isBlank() || (port.text.toString().toIntOrNull() ?: 0) !in 1..65535) {
                    status.text = "Enter a valid host and port."
                    return@setOnClickListener
                }
                val permission = VpnService.prepare(this@MainActivity)
                if (permission != null) startActivityForResult(permission, 101)
                else startVpn()
            }
        })
        root.addView(Button(this).apply {
            text = "Disconnect"
            setOnClickListener { stopService(Intent(this@MainActivity, ProxyVpnService::class.java)) }
        })
        status = TextView(this).apply {
            text = "Disconnected"
            setTextColor(0xFFFFFFFF.toInt())
        }
        root.addView(status)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun startVpn() {
        val service = Intent(this, ProxyVpnService::class.java)
            .putExtra("host", host.text.toString())
            .putExtra("port", port.text.toString().toInt())
            .putExtra("username", user.text.toString())
            .putExtra("password", pass.text.toString())
        startService(service)
        status.text = "VPN permission granted. Tunnel engine is not integrated yet."
    }

    @Deprecated("Legacy activity result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) startVpn()
    }
}
