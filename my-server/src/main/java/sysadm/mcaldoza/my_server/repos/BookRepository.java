package sysadm.mcaldoza.my_server.repos;

import org.springframework.stereotype.Repository;

import sysadm.mcaldoza.my_server.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
