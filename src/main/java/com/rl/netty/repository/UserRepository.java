package com.rl.netty.repository;

import com.rl.netty.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class UserRepository {

    public static final String USER_LOAD_USERS_BY_CRITERIA_ASC = "User.loadUsersByCriteriaAsc";
    public static final String USER_LOAD_USERS_BY_CRITERIA_DESC = "User.loadUsersByCriteriaDesc";

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.rl.netty");

    public User saveUser(final User user) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        return user;
    }

    public User updateUser(final User user) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        final User updatedUser = em.merge(user);
        em.getTransaction().commit();
        return updatedUser;
    }

    public User getUser(final Long userId) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        return em.find(User.class, userId);
    }

    public boolean existsUser(final Long userId) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        final boolean existsUser = em.find(User.class, userId) != null;
        em.getTransaction().commit();
        return existsUser;
    }

    public void deleteUser(final Long userId) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, userId));
        em.getTransaction().commit();
    }

    public User loadUser(final Long userId) {
        final EntityManager em = getEntityManager();
        em.getTransaction().begin();
        final User user = em.find(User.class, userId);
        em.getTransaction().commit();
        return user;
    }

    public List<User> loadUsers(final Map<String, String> uriParams) {
        final EntityManager em = getEntityManager();
        final String queryTemplate = isAscOrder(uriParams) ? USER_LOAD_USERS_BY_CRITERIA_ASC : USER_LOAD_USERS_BY_CRITERIA_DESC;
        final TypedQuery<User> query = em.createNamedQuery(queryTemplate, User.class);

        setParamIfExists(query, "name", uriParams);
        setParamIfExists(query, "email", uriParams);
        setParamIfExists(query, "date", uriParams);

        return query.getResultList();
    }

    private boolean isAscOrder(final Map<String, String> uriParams) {
        String orderValue = "ASC";
        if (uriParams.containsKey("order") && (uriParams.get("order").toUpperCase().equals("ASC") || uriParams.get("order").toUpperCase().equals("DESC"))) {
            orderValue = uriParams.get("order");
        }
        return "ASC".equals(orderValue);
    }

    private void setParamIfExists(final TypedQuery<User> query, final String paramName, final Map<String, String> uriParams) {
        query.setParameter(paramName, uriParams.containsKey(paramName) ? uriParams.get(paramName) : null);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
