package com.example.simpleproxy

import android.content.Intent
import android.net.VpnService
import android.util.Log

/**
 * Scaffold only. No TUN interface or packet forwarding is implemented yet.
 * Integrate and test a maintained tun2socks engine with SOCKS5 TCP and UDP
 * ASSOCIATE support before claiming this is a working proxy.
 */
class ProxyVpnService : VpnService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val host = intent?.getStringExtra("host").orEmpty()
        val port = intent?.getIntExtra("port", 0) ?: 0
        if (host.isBlank() || port !in 1..65535) {
            stopSelf()
            return START_NOT_STICKY
        }
        Log.i("SimpleProxy", "SOCKS5 endpoint configured: $host:$port")
        // TODO: Configure VpnService.Builder, app allowlist, TUN descriptor,
        // and a tun2socks engine. Call protect() on the engine's upstream socket.
        return START_NOT_STICKY
    }
}
