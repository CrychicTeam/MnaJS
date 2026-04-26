# MnaJS Example Scripts

These files mirror the KubeJS examples used during local testing.
Copy the script you need into the matching `kubejs` folder in a test instance.

- `startup_scripts/example.js` -> `kubejs/startup_scripts/example.js`
- `server_scripts/example.js` -> `kubejs/server_scripts/example.js`
- `client_scripts/example.js` -> `kubejs/client_scripts/example.js`

Startup scripts require a full game restart. Server scripts reload with `/reload`.
Client scripts require a client reload or restart, depending on the active KubeJS setup.

Each script has an enable flag near the top:

- `ENABLE_MNA_STARTUP_EXAMPLES`
- `ENABLE_MNA_SERVER_EXAMPLES`
- `ENABLE_MNA_CLIENT_EXAMPLES`

Set the flag to `false` before copying a script into a shared development instance
if you only want ProbeJS completion examples and do not want the script to register
test content.
