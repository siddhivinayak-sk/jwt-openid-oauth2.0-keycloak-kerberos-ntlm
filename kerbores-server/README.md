Prerequisite:
1. Domain Must be created
2. AD must be installed and join the domain
3. LDAP must be installed and configured
4. KDC must enable - telnet <machine host: on which AD installed> 88
5. GSS Flag must be enabled


1. A user need to be created in AD with no password expire and change - service account
2. A machine requried where service need to be deployed - this machine must be joined same Domain on which AD users created
3. Need to create/map created user in step 1 with SPN where SPN will be like:
HTTP/<FQDN>@REALM
4. Generate Keytab for the created SPN
5. Create KRB5 config
6. Setup code, configure KRB5 config and keytab and test


