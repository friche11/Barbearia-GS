package com.web.BarbeariaGS.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.BarbeariaGS.models.Agendamento;
import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.models.Funcionario;
import com.web.BarbeariaGS.models.Horario;

public interface AgendamentoRepo extends CrudRepository<Agendamento, Integer>{
  
  // Método para buscar agendamentos por cliente
  @Query("SELECT a FROM Agendamento a WHERE a.cliente = :cliente")
    List<Agendamento> findByCliente(Cliente cliente);

      @Query(value= "select CASE WHEN count(1)>0 THEN 'true' ELSE 'false' END from agendamentos where id = :id", nativeQuery = true)
    public boolean exist(int id);

    @Query(value= "select CASE WHEN count(1)>0 THEN 'true' ELSE 'false' END from agendamentos where email = :email", nativeQuery = true)
    public boolean existsByEmail(String email);

    @Query(value= "select * from agendamentos where email = :email and senha = :senha", nativeQuery = true)
    public Funcionario login(String email, String senha);

    @Query(value = "select * from agendamentos where email = :email", nativeQuery = true)
    Funcionario findByEmail(String email);

    @Query(value = "select senha from agendamentos where email = :email", nativeQuery = true)
    String findSenhaByEmail(String email);

    @Query(value = "select * from agendamentos where email = :email and id <> :id", nativeQuery = true)
    Funcionario findByEmailAndIdNot(String email, int id);

    @Query(value = "SELECT h.id, h.horario " +
    "FROM horarios h " +
    "LEFT JOIN agendamentos a " +
    "ON h.id = a.horario_id AND a.data = :dataAgendamento AND a.funcionario_id = :funcionarioId " +
    "WHERE a.id IS NULL " +
    "ORDER BY h.horario", nativeQuery = true)
List<Object[]> findHorariosVagosByFuncionarioAndData(int funcionarioId, Date dataAgendamento);


}
