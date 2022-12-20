package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx =em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName(("TeamA"));
            em.persist(team);

            Member member =new Member();
            member.setUsername("member1");
            member.changeTeam(team); // 세팅.
            em.persist(member);

            //결국 양쪽 다 넣어주는게 좋다. 객체 지향적으로도 좋음. Member setter에서 진행.
//             team.getMembers().add(member);
            //영속성 컨텍스트에서 안가져오고 db에서 가져오기 위함.
            // em.flush();
            // em.clear();

            Team findTeam =em.find(Team.class, team.getId()); //1차 캐시. 컬렉션에도 아직 값이 없을 수 있다.
            List<Member> members = findTeam.getMembers();
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
            System.out.println("===========================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("===========================");

//            Team newTeam = em.find(Team.class , 100L);
//            findMember.setTeam(newTeam);

            tx.commit();
        }
        catch (Exception e){
            tx.rollback();
        }finally {
        em.close();
        }
        emf.close();
       }
}
