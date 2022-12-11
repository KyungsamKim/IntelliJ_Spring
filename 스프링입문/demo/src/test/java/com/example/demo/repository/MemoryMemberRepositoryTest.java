package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemberRepository repository = new MemoryMemberRepository();

    /**
     * 한번에 여러 테스트를 실행하면 메모리DB에 직전 테스트의 결과가 남을 수 있다.
     * 이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있따.
     * @afeterEach를 사용하면 각 테스트가 종료될 때마다 이 기능을 실행한다.
     * 여기서는 메모리 DB에 저장된 데이터를 삭제한다.
     * 테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는것은 좋은 테스트가 아니다.
     */
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        //given
        Member member =new Member();
        member.setName("spring");

        //when
        repository.save(member);
        Member result =repository.findById(member.getId()).get();

        //then
        //        System.out.println("(result==member) = " + (result==member));
//        Assertions.assertEquals(member,result);
//        Assertions.assertThat(member).isEqualTo(result);
        assertThat(result).isEqualTo(member);
    }

    @Test
    public  void findByName(){
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2= new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result= repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        //shift + f6 으로 한번에 변경 가능
        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
