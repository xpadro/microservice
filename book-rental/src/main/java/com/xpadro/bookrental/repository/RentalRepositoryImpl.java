package com.xpadro.bookrental.repository;

import com.xpadro.bookrental.UserAlreadyRentedException;
import com.xpadro.bookrental.entity.Rental;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import static java.lang.String.format;

@Repository
public class RentalRepositoryImpl implements RentalRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Rental rent(Rental rental) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = builder.createQuery(Rental.class);
        Root<Rental> rentalRoot = query.from(Rental.class);
        query.select(rentalRoot).where(builder.equal(rentalRoot.get("userId"), rental.getUserId()));

        try {
            Rental result = em.createQuery(query).getSingleResult();
            throw new UserAlreadyRentedException(format("User %s already rented book %s", result.getUserId(), result.getIsbn()));
        } catch (NoResultException e) {
            em.persist(rental);
            return rental;
        }
    }
}
