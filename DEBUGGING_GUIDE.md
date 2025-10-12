# 403 Error Debugging Guide

## Changes Made to Fix 403 Error

### 1. SecurityConfig.java - Added Authentication Provider
**Problem**: Missing `AuthenticationProvider` configuration prevented Spring Security from properly authenticating users.

**Fix**: Added `authenticationProvider()` bean that configures:
- `DaoAuthenticationProvider` with `CustomUserDetailsService`
- Password encoder for credential validation
- Registered in the security filter chain

### 2. SecurityConfig.java - Enhanced CORS Configuration
**Problem**: CORS might be blocking requests or headers.

**Fix**: Added explicit CORS configuration:
- Allows all origins, methods, and headers
- Exposes Authorization header
- Applied to all endpoints

### 3. JwtAuthenticationFilter.java - Added Debug Logging
**Problem**: Hard to diagnose where authentication is failing.

**Fix**: Added comprehensive logging to track:
- Request URI and Authorization header
- JWT token extraction
- User email extraction
- User loading from database
- Token validation
- Authentication setting

## How to Test

### Step 1: Restart the Application
```bash
./mvnw spring-boot:run
```

### Step 2: Login and Get JWT Token
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "your-email@example.com",
    "password": "your-password"
  }'
```

Save the `accessToken` from the response.

### Step 3: Test Protected Endpoint
```bash
curl -X GET http://localhost:8081/api/users/count \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN_HERE"
```

### Step 4: Check Console Logs
Look for these debug messages in the console:
```
JwtAuthenticationFilter - Request URI: /api/users/count
JwtAuthenticationFilter - Auth Header: Bearer eyJhbGc...
JwtAuthenticationFilter - JWT Token: eyJhbGc...
JwtAuthenticationFilter - Extracted email: user@example.com
CustomUserDetailsService loadUserByUsername email: user@example.com
CustomUserDetailsService loadUserByUsername user: John Doe
JwtAuthenticationFilter - Loaded user: user@example.com
JwtAuthenticationFilter - User authorities: [USER]
JwtAuthenticationFilter - Authentication set successfully
```

## Common Issues and Solutions

### Issue 1: "No valid Authorization header found"
**Cause**: Authorization header is missing or malformed.
**Solution**: Ensure you're sending the header as: `Authorization: Bearer <token>`

### Issue 2: "Token validation failed"
**Cause**: Token might be expired or invalid.
**Solution**: 
- Check token expiration (default: 24 hours)
- Ensure JWT secret matches between token generation and validation
- Generate a new token via `/api/auth/login`

### Issue 3: "User not found"
**Cause**: Email in JWT doesn't match any user in database.
**Solution**: 
- Verify the user exists in the database
- Check that the email in the token matches the database

### Issue 4: Still getting 403
**Possible causes**:
1. User account is inactive (`isActive = false`)
2. JWT secret mismatch
3. Token expired
4. Database connection issue

## Testing with Postman/Insomnia

1. **Login Request**:
   - Method: POST
   - URL: `http://localhost:8081/api/auth/login`
   - Headers: `Content-Type: application/json`
   - Body (JSON):
     ```json
     {
       "email": "test@example.com",
       "password": "password123"
     }
     ```

2. **Protected Request**:
   - Method: GET
   - URL: `http://localhost:8081/api/users/count`
   - Headers: 
     - `Authorization: Bearer <paste-your-token-here>`

## Next Steps

1. Restart your application
2. Test the login endpoint
3. Copy the accessToken from the response
4. Test the /api/users/count endpoint with the token
5. Check the console logs for debugging information
6. Share the console output if still getting 403

## Remove Debug Logs (After Fixing)

Once the issue is resolved, you can remove the `System.out.println` statements from:
- `JwtAuthenticationFilter.java` (lines 40-41, 44, 50, 54, 58-59, 68, 70, 74-75)
- `CustomUserDetailsService.java` (lines 22, 25)
