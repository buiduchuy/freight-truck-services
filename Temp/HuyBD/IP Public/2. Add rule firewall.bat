netsh advfirewall firewall add rule name="Capstone Public IP" dir=in protocol=tcp localport=8080 profile=private remoteip=localsubnet action=allow

pause