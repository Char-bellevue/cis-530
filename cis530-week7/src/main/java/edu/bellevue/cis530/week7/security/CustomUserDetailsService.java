/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7.security;

import edu.bellevue.cis530.week7.entity.Member;
import edu.bellevue.cis530.week7.repository.MemberRepository;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class that connects Spring Security to the members table. It loads a
 * member by username and converts the member data into a Spring Security UserDetails object.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repository that reads member records from the database.
    private final MemberRepository gMemberRepository;

    /**
     * Creates the service with the repository that Spring injects.
     * @param memberRepository MemberRepository, the repository for the members table.
     */
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.gMemberRepository = memberRepository;
    } // end CustomUserDetailsService

    /**
     * Loads a member by username and converts it to a UserDetails object.
     * @param username String, the login name typed by the person signing in.
     * @return UserDetails, the username, encrypted password, role, and enabled status of the member.
     * @throws UsernameNotFoundException when no member has the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the member or stop with an exception when the username does not exist.
        Member lMember = gMemberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found: " + username));

        // Wrap the stored role, such as ROLE_ADMIN, as a Spring Security authority.
        List<GrantedAuthority> lAuthorities = List.of(new SimpleGrantedAuthority(lMember.getRole()));

        // Build the UserDetails object, and mark the account disabled unless enabled is true.
        return User.builder()
                .username(lMember.getUsername())
                .password(lMember.getPassword())
                .disabled(!Boolean.TRUE.equals(lMember.getEnabled()))
                .authorities(lAuthorities)
                .build();
    } // end loadUserByUsername
} // end CustomUserDetailsService
