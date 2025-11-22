# Security Policy

## Supported Versions

We release patches for security vulnerabilities for the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

We take the security of Strategic Wealth Management System seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### Where to Report

**Please do NOT report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to:
- **Primary**: [svaranas3@gitam.in](mailto:svaranas3@gitam.in)
- **Secondary**: [varanasikarthik44@gmail.com](mailto:varanasikarthik44@gmail.com)

### What to Include

Please include the following information:

- Type of vulnerability
- Full paths of source file(s) related to the vulnerability
- Location of the affected source code (tag/branch/commit or direct URL)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the vulnerability, including how an attacker might exploit it

### Response Timeline

- **Initial Response**: Within 48 hours
- **Status Update**: Within 7 days
- **Fix & Disclosure**: Within 90 days (coordinated disclosure)

### What to Expect

1. **Acknowledgment**: We'll confirm receipt of your vulnerability report
2. **Investigation**: We'll investigate and validate the vulnerability
3. **Fix Development**: We'll develop and test a fix
4. **Disclosure**: We'll publicly disclose the vulnerability after a fix is available
5. **Recognition**: We'll credit you for the discovery (if you wish)

## Security Best Practices

### For Users

1. **Keep Software Updated**: Always use the latest version
2. **Secure Your Data**: 
   - Protect CSV exports
   - Don't share portfolio data publicly
   - Use strong passwords if authentication is added
3. **Review Permissions**: Be cautious with file system access
4. **Monitor Activity**: Regularly review transaction history

### For Developers

1. **Input Validation**: Always validate and sanitize user input
2. **Dependency Management**: Keep dependencies updated
3. **Code Review**: Review all PRs for security implications
4. **Sensitive Data**: Never commit passwords, API keys, or PII
5. **Error Handling**: Don't expose sensitive information in error messages

## Known Limitations

### Current Version (1.0.x)

- **No Authentication**: Single-user CLI application
- **No Encryption**: Data stored in plain text CSV
- **No Network Security**: Future API integrations will need HTTPS
- **Limited Input Validation**: Relies on Java type checking

### Planned Security Enhancements

- [ ] User authentication system
- [ ] Encrypted data storage
- [ ] HTTPS for API communications
- [ ] Role-based access control
- [ ] Audit logging
- [ ] Two-factor authentication
- [ ] API key management
- [ ] Rate limiting

## Security Updates

Security updates will be released as:
- Patch versions (1.0.x) for critical vulnerabilities
- Minor versions (1.x.0) for security enhancements

Subscribe to releases on GitHub to stay informed.

## Vulnerability Disclosure Policy

### Coordinated Disclosure

We follow a coordinated disclosure policy:

1. **Private Report**: Vulnerability reported privately
2. **Fix Development**: We develop and test a fix (up to 90 days)
3. **Public Disclosure**: After fix is released, we publish:
   - Security advisory
   - CVE (if applicable)
   - Credit to researcher

### Public Disclosure Timeline

- **Day 0**: Vulnerability reported
- **Day 7**: Status update to reporter
- **Day 30-90**: Fix developed and tested
- **Release Day**: Patch released, public disclosure

## Bug Bounty

Currently, we do not offer a bug bounty program. However, we deeply appreciate security researchers who responsibly disclose vulnerabilities and will:

- Publicly credit you (if you wish)
- Feature you in our README acknowledgments
- Provide a recommendation/reference if requested

## Past Security Advisories

None at this time.

## Contact

For security concerns, contact:

**Varanasi Sai Srinivasa Karthik**
- Email: [svaranas3@gitam.in](mailto:svaranas3@gitam.in)
- GitHub: [@vssk18](https://github.com/vssk18)

---

**Last Updated**: November 2024  
**Version**: 1.0
