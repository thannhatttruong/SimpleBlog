/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import truongtn.entity.Account;
import truongtn.entity.Article;
import truongtn.utils.HibernateUtil;

/**
 *
 * @author truongtn
 */
public class ArticleDAO {

    private Session session;

    public ArticleDAO() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void begin() {
        session.getTransaction().begin();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollback() {
        session.getTransaction().rollback();
    }

    public boolean isActive() {
        return session.getTransaction().isActive();
    }

    public Article[] search(String searchValue)throws Exception {
        Article[] articleArr = null;
        try {
            begin();
            String sql = "From Article Where content like ? and status = ? ORDER BY postingDate";
            Query query = session.createQuery(sql);
            query.setString(0, "%" + searchValue + "%");
            query.setString(1, "Active");
            List list = query.list();
            articleArr = new Article[list.size()];
            list.toArray(articleArr);
            commit();
        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return articleArr;
    }

    public boolean insertAnArticle(String email, String title, String shortDescription, String content, String author, Date postingDate)throws Exception {
        try {
            begin();
            Account acc = (Account) session.get(Account.class, email);
            Article article = new Article(acc, title, shortDescription, content, postingDate, "New");
            acc.getArticles().add(article);
            session.save(article);
            session.flush();
            commit();
            return true;
        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public Article[] getArticles()throws Exception {
        Article[] articleArr = null;
        try {
            begin();
            String sql = "From Article where status = ? ORDER BY postingDate";
            Query query = session.createQuery(sql);
            query.setString(0, "Active");
            List list = query.list();
            articleArr = new Article[list.size()];
            list.toArray(articleArr);

            commit();
        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return articleArr;
    }

    public Article getDetail(int articleId)throws Exception {
        try {
            begin();
            String sql = "From Article Where id = ?";
            Query query = session.createQuery(sql);
            query.setInteger(0, articleId);
            Article article = (Article) query.uniqueResult();
            commit();
            return article;

        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public Article[] getArticlesByAdmin()throws Exception {
        Article[] articleArr = null;
        try {
            begin();
            String sql = "From Article ORDER BY postingDate ASC, status DESC";
            Query query = session.createQuery(sql);
            List list = query.list();
            articleArr = new Article[list.size()];
            list.toArray(articleArr);

            commit();
        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return articleArr;
    }

    public Article[] searchByAdmin(String searchValue)throws Exception {
        Article[] articleArr = null;
        try {
            begin();
            String sql = "From Article Where content like ? ORDER BY postingDate ASC, status DESC";
            Query query = session.createQuery(sql);
            query.setString(0, "%" + searchValue + "%");
            List list = query.list();
            articleArr = new Article[list.size()];
            list.toArray(articleArr);
            commit();
        } catch (Exception e) {
            if (isActive()) {
                rollback();
            }
            e.printStackTrace();
        }
        return articleArr;
    }
    
    public boolean deleteArticle(ArrayList<Integer> idUpdate, String status)throws Exception{
        try {
            begin();
            for (Integer id : idUpdate) {
                Article article = (Article) session.get(Article.class, id);
                article.setStatus(status);
                session.save(article);
                session.flush();
            }
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
    
    public boolean activeArticle(ArrayList<Integer> idUpdate, String status)throws Exception{
        try {
            begin();
            for (Integer id : idUpdate) {
                Article article = (Article) session.get(Article.class, id);
                article.setStatus(status);
                article.getAccount().setStatus("Active");
                session.save(article);
                session.flush();
            }
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
//        ArticleDAO dao = new ArticleDAO();
//        ArrayList<Integer> id = new ArrayList<>();
//        id.add(1);
//        id.add(6);
//        if(dao.updateArticleStatus(id, "Deleted")){
//            System.out.println("Success");
//        }
//    }
}
