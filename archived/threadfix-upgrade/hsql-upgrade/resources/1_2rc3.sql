INSERT INTO ChannelSeverity (name, code, channelTypeId, numericValue) VALUES ('Information', 'Information', (SELECT id FROM ChannelType WHERE name = 'IBM Rational AppScan'), 1);
INSERT INTO ChannelType (name, url, version, exportInfo) VALUES ('Dependency Check', 'https://github.com/jeremylong/DependencyCheck', '1.02', 'The Dependency Check importer takes the XML output format.');
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Absence of Anti-CSRF Tokens', 'Absence of Anti-CSRF Tokens', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Anti CSRF tokens scanner', 'Anti CSRF tokens scanner', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Cross Site Scripting (Reflected)', 'Cross Site Scripting (Reflected)', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Cross Site Scripting', 'Cross Site Scripting', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Cross site scripting in SCRIPT section', 'Cross site scripting in SCRIPT section', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('Cross-domain JavaScript source file inclusion', 'Cross-domain JavaScript source file inclusion', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('SQL Injection - Generic SQL RDBMS', 'SQL Injection - Generic SQL RDBMS', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO ChannelVulnerability (name, code, channelTypeId) VALUES ('pscanbeta.charsetmismatch.name', 'pscanbeta.charsetmismatch.name', (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy'));
INSERT INTO SeverityMap (ChannelSeverityId, GenericSeverityId) VALUES ((SELECT id FROM ChannelSeverity WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'IBM Rational AppScan') AND name = 'Information'), (SELECT id FROM GenericSeverity WHERE name = 'Info'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'NTO Spider') AND code = 'Reflection Analysis'), (SELECT id FROM GenericVulnerability WHERE name = 'Improper Encoding or Escaping of Output'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Absence of Anti-CSRF Tokens'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Anti CSRF tokens scanner'), (SELECT id FROM GenericVulnerability WHERE name = 'Cross-Site Request Forgery (CSRF)'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'BEA WebLogic example files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Buffer Overflow'), (SELECT id FROM GenericVulnerability WHERE name = 'Buffer Copy without Checking Size of Input (''Classic Buffer Overflow'')'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Cold Fusion default file'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'ColdFusion default files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Content-Type header missing'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Cookie set without HttpOnly flag'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Cookie set without secure flag'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Cross Site Scripting (Reflected)'), (SELECT id FROM GenericVulnerability WHERE name = 'Improper Neutralization of Input During Web Page Generation (''Cross-site Scripting'')'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Cross-domain JavaScript source file inclusion'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Directory browsing'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure Through Directory Listing'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Exceeding long paramter'), (SELECT id FROM GenericVulnerability WHERE name = 'Improper Input Validation'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'HTTP methods'), (SELECT id FROM GenericVulnerability WHERE name = 'Trusting HTTP Permission Methods on the Server Side'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'IBM WebSphere default files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'IIS default file'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Incomplete or no cache-control and pragma HTTPHeader set'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Information disclosure - database error messages'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Information disclosure - debug error messages'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Information disclosure - sensitive informations in URL'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Lotus Domino default files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Macromedia JRun default files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Microsoft IIS default files'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Obsolete file extended check'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Obsolete file'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Password Autocomplete in browser'), (SELECT id FROM GenericVulnerability WHERE name = 'Information Exposure Through Browser Caching'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'SQL Injection - Generic SQL RDBMS'), (SELECT id FROM GenericVulnerability WHERE name = 'Improper Neutralization of Special Elements used in an SQL Command (''SQL Injection'')'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'Tomcat source file disclosure'), (SELECT id FROM GenericVulnerability WHERE name = 'Weaknesses that Affect Files or Directories'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'X-Content-Type-Options header missing'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'X-Frame-Options header not set'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
INSERT INTO VulnerabilityMap (mappable, ChannelVulnerabilityId, GenericVulnerabilityId) VALUES (1, (SELECT id FROM ChannelVulnerability WHERE ChannelTypeId = (SELECT id FROM ChannelType WHERE name = 'OWASP Zed Attack Proxy') AND code = 'pscanbeta.charsetmismatch.name'), (SELECT id FROM GenericVulnerability WHERE name = 'Configuration'));
ALTER TABLE Vulnerability ADD HIDDEN Boolean;
UPDATE Vulnerability SET HIDDEN = FALSE;
