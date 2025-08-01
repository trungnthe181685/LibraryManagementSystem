package com.example.openlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.model.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.book = :book AND r.status = com.example.openlibrary.model.Reservation.Status.RESERVED")
	int countActiveReservationsByBook(@Param("book") Book book);
	
	List<Reservation> findTop5ByOrderByCreatedDateDesc();

	List<Reservation> findByUser(User user);

	boolean existsByBookAndUser(Book book, User user);

	List<Reservation> findByBookOrderByReservedAtAsc(Book book);
	
	@Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.borrowRecords WHERE r.reservationID = :id")
	Reservation findByIdWithBorrowRecords(@Param("id") Long id);
	
	Page<Reservation> findAll(Pageable pageable);

}