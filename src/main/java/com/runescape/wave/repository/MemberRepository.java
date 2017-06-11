package com.runescape.wave.repository;

import com.runescape.wave.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Martijn Jansen on 6/10/2017.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findOneByName (String name);
}
