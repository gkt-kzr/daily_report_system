package services;

import java.time.LocalDateTime;

import actions.views.LikeConverter;
import actions.views.LikeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;

/**
 * いいねテーブルの操作にかかわる処理を行うクラス
 */
public class LikeService extends ServiceBase{

    /**
     * 指定した日報をいいねした従業員の件数を取得し返却
     * @param report
     * @return いいねをした従業員の件数
     */
    public long countAllMine(ReportView report) {
        long likCount = (long) em.createNamedQuery(JpaConst.Q_LIK_COUNT_ALL_MINE_DEF, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return likCount;
    }

    /**
     * いいねデータを一件登録する
     * @param lv いいねデータ
     */
    public void create(LikeView lv) {
        LocalDateTime ldt = LocalDateTime.now();
        lv.setCreatedAt(ldt);
        lv.setUpdatedAt(ldt);
        em.getTransaction().begin();
        em.persist(LikeConverter.toModel(lv));
        em.getTransaction().commit();

    }


}
