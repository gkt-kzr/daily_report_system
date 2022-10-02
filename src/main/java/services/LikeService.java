package services;

import java.time.LocalDateTime;

import actions.views.LikeConverter;
import actions.views.LikeView;
import constants.JpaConst;
import models.Employee;
import models.Report;
/**
 * いいねテーブルの操作にかかわる処理を行うクラス
 */
public class LikeService extends ServiceBase{

    /**
     * 指定した日報をいいねした従業員の件数を取得し返却
     * @param report_id
     * @return いいねをした従業員の件数
     */
    public long countAllLiked(Report report) {
        long likCount = (long) em.createNamedQuery(JpaConst.Q_LIK_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                .getSingleResult();

        return likCount;
    }

    /**
     * いいねデータを一件登録する
     * @param lv いいねデータ
     */
    public void createLike(LikeView lv) {
        LocalDateTime ldt = LocalDateTime.now();
        lv.setCreatedAt(ldt);
        lv.setUpdatedAt(ldt);
        em.getTransaction().begin();
        em.persist(LikeConverter.toModel(lv));
        em.getTransaction().commit();

    }

    /**
     * いいねデータを1件取得する
     * @param employee_id
     * @param report_id
     * @return いいねの有無
     */
    public long findOneLiked(Employee employee, Report report) {
       long liked = (long) em.createNamedQuery(JpaConst.Q_LIK_GET_BY_EMP_AND_REP, Long.class)
               .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, employee)
               .setParameter(JpaConst.JPQL_PARM_REPORT, report)
               .getSingleResult();

       System.out.println("下がliked");
       System.out.println(liked);



        return liked;
    }

    /**
     * いいねデータを1件削除する
     * @param employee_id
     * @param report_id
     */
    public void destroyLiked(Employee employee, Report report) {
        em.getTransaction().begin();

         int likes = em.createNamedQuery(JpaConst.Q_LIK_DESTROY_BY_EMP_AND_REP)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, employee)
                .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                .executeUpdate();

         em.getTransaction().commit();
    }

}