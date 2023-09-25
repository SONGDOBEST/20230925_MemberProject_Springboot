package com.icia.member.repository;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//interface == 추상method를 정의. 구현하는 블록이 없고 "정의"만을 구현함
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //sql문이 select인 메소드들은 보통 findBy로 정의됨.
    //select * from member_tb where member_email=?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    //select * from member_tb where member_email=? and member_password=?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);


//@Query(value="select * from member_tb where member_email= :#{#member.memberEmail} and member_name= :#{#member.memberPassword}", nativeQuery = true)
//    @Modifying
//    @Transactional
//    public List<MemberDTO> login(@Param(value="member") MemberEntity memberEntity);
}
