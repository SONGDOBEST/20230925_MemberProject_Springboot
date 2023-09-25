package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
//        System.out.println("MemberDTO = "+memberDTO);
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        Long savedId = memberRepository.save(memberEntity).getId();
        System.out.println("savedId = " + savedId);
        return savedId;

    }

    public List<MemberDTO> findAll() {
        /*
            List<StudentEntity> -> List<StudentDTO> 로 옮겨 담는 코드 작성
            Entity -> DTO 변환하는 메서드는 DTO에 정의
         */
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

//        for(MemberEntity memberEntity : memberEntityList){
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            memberDTOList.add(memberDTO);
//        }
        memberEntityList.forEach(entity->{
            System.out.println("entity = " + entity);
            memberDTOList.add(MemberDTO.toDTO(entity));
        });
//        memberEntityList에 저장되어있는 값에서 foreach를 돌림. entity라는 변수로 두고, MemberDTOList에 Entity형식으로 되어있던 값을
//        DTO에 정의해두었던 toDTO 메소드를 이용하여 DTO 형식으로 변환 뒤(MemberDTO.toDTO(entity)) memberDTOList에 add해주는 것.
        return memberDTOList;
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        DB에서 로그인하는 사용자의 이메일로 조회한 결과를 가져와서
        비밀번호가 일치하는지 비교한 뒤 로그인 성공여부 판단

        findByMemberEmail()
        select * from member_tb where member_email = ?

        findById()
        => select * from member_tb where id = ?
         */
//        1.
//        memberRepository.findByMemberEmail(memberDTO.getMemberEmail())
//                        .orElseThrow(()-> new NoSuchElementException()); //객체 결과가 없으면 예외를 생성하도록 함
//        2. email과 password를 둘 다 만족하는 결과가 있으면 true, 아니면 false로 결과값 받기
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get(); // optional을 까주는 거
            MemberDTO loginDTO = MemberDTO.toDTO(memberEntity);
            return loginDTO;
        }else{
            return null;
        }
    }

    public MemberDTO findByEmail(MemberDTO memberDTO) {
        memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        return memberDTO;
    }
}
