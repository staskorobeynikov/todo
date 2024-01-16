package ru.skdev.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.skdev.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Repository
public class HibernateTaskStore implements TaskStore {
    private final SessionFactory factory;

    @Override
    public Optional<Task> create(Task task) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            return Optional.of(task);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Task> result = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            result = session.createQuery(
                    """
                            FROM Task t
                            ORDER BY t.id
                            """,
                    Task.class
            ).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findUndoneTasks() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Task> result = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            result = session.createQuery(
                    """
                            FROM Task t
                            WHERE t.done = false
                            ORDER BY t.id
                            """,
                    Task.class
            ).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findDoneTasks() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Task> result = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            result = session.createQuery("""
                            FROM Task t
                            WHERE t.done = true
                            ORDER BY t.id
                            """,
                    Task.class
            ).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(int id, Task task) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int result = session
                    .createQuery(
                            """
                                    UPDATE Task t
                                    SET t.title = :title,
                                        t.description = :description
                                    WHERE t.id = :id
                                    """
                    ).setParameter("title", task.getTitle())
                    .setParameter("description", task.getDescription())
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int result = session.createQuery(
                            """
                                    DELETE FROM Task t
                                    WHERE t.id = :id
                                    """
                    ).setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateDone(int id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int result = session.createQuery(
                            """
                                    UPDATE Task t
                                    SET t.done = true
                                    WHERE t.id = :id
                                    """
                    ).setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Optional<Task> result = Optional.empty();
        try {
            transaction = session.beginTransaction();
            result = session.createQuery("""
                                    FROM Task t
                                    WHERE t.id = :id
                                    """,
                            Task.class
                    ).setParameter("id", id)
                    .uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }
}
