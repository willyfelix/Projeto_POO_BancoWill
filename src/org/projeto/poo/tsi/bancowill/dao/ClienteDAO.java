package org.projeto.poo.tsi.bancowill.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.projeto.poo.tsi.bancowill.model.Cliente;

public class ClienteDAO {
	
	private static ClienteDAO instance;
	
	private SessionFactory factory;	
	
	private ClienteDAO() {
		this.factory = new Configuration()
	            .configure()
	            .addAnnotatedClass(Cliente.class)
	            .buildSessionFactory();
	}

	public static ClienteDAO getInstance() {
		if (instance == null) {
			instance = new ClienteDAO();
		}	
	
		return instance;
	}
	
	public List<Cliente> listarClientes() {
	    Session session = factory.openSession();
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
	    criteria.from(Cliente.class);
	    List<Cliente> data = session.createQuery(criteria).getResultList();
	    return data;
	}
		
	public void salvarCliente(Cliente cliente) {
		
		Session session = factory.openSession();
		Transaction tx=session.beginTransaction();

		session.save(cliente);

		tx.commit();
		session.close();

	}

	public Cliente localizarClientePorCPF(String cpf) {

		Session session = factory.openSession();
		Cliente cliente = session.byNaturalId(Cliente.class)
		                   .using("cpf", cpf)
		                   .load();
		session.close();
		
		return cliente;
	}
	
	public Cliente removerClientePorCPF(String cpf) {
		Cliente cliente = localizarClientePorCPF(cpf);
		
		Session session = factory.openSession();
		Transaction tx=session.beginTransaction();

		session.delete(cpf);

		tx.commit();
		session.close();
		return cliente;
	}
	
	public void atualizar(Cliente cliente) {

		Session session = factory.openSession();
		Transaction tx=session.beginTransaction();

		session.merge(cliente);

		tx.commit();
		session.close();
	}
	
}
