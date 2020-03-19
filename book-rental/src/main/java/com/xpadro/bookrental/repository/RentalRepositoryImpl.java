package com.xpadro.bookrental.repository;

import com.xpadro.bookrental.BookAlreadyRentedException;
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
        query.select(rentalRoot).where(builder.equal(rentalRoot.get("isbn"), rental.getIsbn()));

        try {
            Rental result = em.createQuery(query).getSingleResult();
            throw new BookAlreadyRentedException(format("Book %s already rented by user %s", result.getIsbn(), result.getUserId()));
        } catch (NoResultException e) {
            em.persist(rental);
            return rental;
        }
    }
}
