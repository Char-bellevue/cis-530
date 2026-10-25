/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * Controller that serves the protected page shown after a successful login.
 * Only logged-in users can reach it, because SecurityConfig requires authentication.
 */
@RestController
public class ProfileController {

    /**
     * Builds a simple page with the logged-in user's basic profile and a logout link.
     * @param principal OAuth2User, the Google or GitHub user, or null for a database login.
     * @param authentication Authentication, the current login, used as a fallback name.
     * @return String, an HTML page that shows the name, email, and a logout link.
     */
    @GetMapping(value = {"/", "/profile"}, produces = MediaType.TEXT_HTML_VALUE)
    public String showProfile(@AuthenticationPrincipal OAuth2User principal,
                              Authentication authentication) {
        // Start with the login name, which every kind of login has.
        String lDisplayName = authentication.getName();
        String lEmail = "Not shared by the provider";

        // Google and GitHub logins carry extra profile attributes.
        if (principal != null) {
            String lFullName = principal.getAttribute("name");
            String lLoginName = principal.getAttribute("login");
            String lProviderEmail = principal.getAttribute("email");

            // Prefer the full name, then the GitHub login name.
            if (lFullName != null) {
                lDisplayName = lFullName;
            } else if (lLoginName != null) {
                lDisplayName = lLoginName;
            } // end if

            if (lProviderEmail != null) {
                lEmail = lProviderEmail;
            } // end if
        } // end if

        // Escape the values so profile text can never be treated as HTML.
        return "<h1>Protected Page</h1>"
                + "<p>Welcome, " + HtmlUtils.htmlEscape(lDisplayName) + "</p>"
                + "<p>Email: " + HtmlUtils.htmlEscape(lEmail) + "</p>"
                + "<p><a href=\"/logout\">Log out</a></p>";
    } // end showProfile
} // end ProfileController
