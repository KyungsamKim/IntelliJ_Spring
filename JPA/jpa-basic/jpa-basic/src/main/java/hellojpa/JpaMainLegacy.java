//package hellojpa;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//import java.util.List;
//
//public class JpaMainLegacy {
//    public static void main(String[] args) {
//        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        //code
//        EntityTransaction tx =em.getTransaction();
//        tx.begin();
//
//        try {
//
////        Member member = new Member();
////        member.setId(2L);
////        member.setName("HelloB");
////        em.persist(member);
//
////        Member findMember = em.find(Member.class, 1L);
////            System.out.println("findMember.id= "+findMember.getId());
////            System.out.println("findMember.name= "+findMember.getName());
//
////        em.remove(findMember);
//
////       findMember.setName("HelloJPA");
////            List<Member> result = em.createQuery("select m from Member as m",Member.class)
////                    .setFirstResult(5)
////                    .setMaxResults(8)
////                        .getResultList();
////
////            for(Member member:result){
////                System.out.println("member.name = "+ member.getName());
////            }
//            tx.commit();
//        }
//        catch (Exception e){
//            tx.rollback();
//        }finally {
//        em.close();
//        }
//        emf.close();
//       }
//}
