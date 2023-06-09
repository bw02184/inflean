package jpabook.jpashop.service;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);
        em.flush();

        assertEquals(member,memberRepository.fiindOne(saveId));
    }

    @Test
    public void 중복_회원_예약() throws Exception{

        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        try {
            memberService.join(member2); //예외가 발생해야 한다!!!
        }catch(IllegalStateException e) {
            return;
        }
        fail("예외가 발생해야 한다.");
    }

}