/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.daos;

import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import truongtn.entity.Account;
import truongtn.entity.FbUserInfo;
import truongtn.utils.HibernateUtil;

/**
 *
 * @author truongtn
 */
public class AccountDAO implements Serializable{
    private Session session;

    public AccountDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public void openSession(){
        this.session = HibernateUtil.getSessionFactory().openSession();
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
    
    public Account authenticate(String username, String password)throws Exception{
        Account acc  = null;
        try {
            begin();
            String sql = "From Account Where email = ? and password = ?";
            Query query = session.createQuery(sql);
            query.setString(0, username);
            query.setString(1, password);   
            acc = (Account)query.uniqueResult();
            session.flush();
            commit();
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return acc;
    }
    
    public Account authenticate(String facebookID)throws Exception{
        Account acc = null;
        try {
            openSession();
            begin();
            String sql = "From Account Where facebookID = ?";
            Query query = session.createQuery(sql);
            query.setString(0, facebookID);
            acc = (Account) query.uniqueResult();
            session.flush();
            commit();
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return acc;
    }
    
    public boolean register(String username, String password, String name, String role, String status)throws Exception{
        boolean result = false;
        try {
            begin();
            Account acc = new Account(username, name, password, role, status);
            session.save(acc);
            session.flush();
            commit();
            result = true;
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return result;
    }
    
    public Account registerByFb(FbUserInfo fbUserInfo)throws Exception{
        try {
            openSession();
            begin();
            Account acc = new Account(fbUserInfo.getEmail(), fbUserInfo.getName(), null, "member", "New", fbUserInfo.getFacebookID());
            session.save(acc);
            session.flush();
            commit();
            return acc;
        } catch (Exception e) {
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
            return null;
        }   
    }
    
    public boolean registerUser(String email)throws Exception {
        try{
            begin();
            Account acc = (Account) session.get(Account.class, email);
            acc.setRole("user");
            session.save(acc);
            session.flush();
            commit();
            return true;
        }catch(Exception e){
            if(isActive()){
                rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean activeAccountStatus(String email)throws Exception{
        try {
            begin();
            Account acc = (Account) session.get(Account.class, email);
            acc.setStatus("active");
            session.save(acc);
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
}
