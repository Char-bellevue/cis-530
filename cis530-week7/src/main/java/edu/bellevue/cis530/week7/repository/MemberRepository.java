/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7.repository;

import edu.bellevue.cis530.week7.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the members table. Spring Security uses it to read
 * the member record that matches the username typed on the login page.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Finds one member by username.
     * @param username String, the login name to look up.
     * @return Optional of Member, which holds the member when found or is empty otherwise.
     */
    Optional<Member> findByUsername(String username);
} // end MemberRepository
