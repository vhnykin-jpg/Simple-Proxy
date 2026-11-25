# Simple Proxy — Android starter

Android 12+ Kotlin starter for a per-app SOCKS5 VPN client.

## Included
- SOCKS5 host, port, username and password input
- Android VPN permission request
- VPNService declaration
- Connect/disconnect UI scaffold
- GitHub Actions debug APK build

## Not implemented yet
This is **not yet a functional proxy**. The VPN service does not create a TUN interface or forward packets. A maintained tun2socks engine must be integrated and tested for TCP and SOCKS5 UDP ASSOCIATE. Per-app routing must use `VpnService.Builder.addAllowedApplication(packageName)` for each selected package.

## Build
Push to GitHub; Android Debug APK workflow runs on pushes and can also be started manually from Actions. Download the APK from the workflow run's Artifacts section.

## Before release
Implement app selection, TUN/tun2socks integration, UDP testing, DNS/IPv6 handling, foreground notification, reconnect lifecycle, and Keystore-backed credential storage. Never log proxy credentials.
