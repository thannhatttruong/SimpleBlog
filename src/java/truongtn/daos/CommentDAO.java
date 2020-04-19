/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.daos;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import truongtn.entity.Account;
import truongtn.entity.Article;
import truongtn.entity.Comment;
import truongtn.utils.HibernateUtil;

/**
 *
 * @author truongtn
 */
public class CommentDAO {
    private Session session;

    public CommentDAO() {
        this.session  = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public void begin(){
        session.getTransaction().begin();
    }
    
    public void commit(){
        session.getTransaction().commit();
    }
    
    public boolean isActive(){
        return session.getTransaction().isActive();
    }
    
    public void rollback(){
        session.getTransaction().rollback();
    }
    
    public Comment[] getCommentByArticleId(int idArticle)throws Exception{
        try {
            begin();
            String sql = "From Comment Where idAricle = ?";
            Query query = session.createQuery(sql);
            query.setInteger(0, idArticle);
            List list = query.list();
            Comment[] commentArr = new Comment[list.size()];
            list.toArray(commentArr);
            commit();
            return commentArr;
            
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insertAComment(int idArticle, String content, Date time, String emailComment)throws Exception{
        try {
            begin();
            Article article = (Article) session.get(Article.class, idArticle);
            Account acc = (Account) session.get(Account.class, emailComment);
            Comment comment = new Comment(acc, article, content, time);
            article.getComments().add(comment);
            session.save(comment);
            session.flush();
            commit();
            return true;
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
    
//    public static void main(String[] args) {
//        try {
//            CommentDAO dao = new CommentDAO();
//            Date time = MyToys.getCurrentDate("yyyy-MM-dd HH:mm:ss");
//            dao.insertAComment(1, "Hay", time, "nhitn@gmail.com");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
