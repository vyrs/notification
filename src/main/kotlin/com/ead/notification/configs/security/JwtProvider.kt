package com.ead.notification.configs.security

import com.ead.notification.configs.EadLog
import com.ead.notification.configs.log
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider: EadLog {
    @Value("\${ead.auth.jwtSecret}")
    private val jwtSecret: String? = null

    fun getSubjectJwt(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun getClaimNameJwt(token: String, claimName: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body[claimName].toString()
    }

    fun validateJwt(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            log().error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            log().error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log().error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            log().error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            log().error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}