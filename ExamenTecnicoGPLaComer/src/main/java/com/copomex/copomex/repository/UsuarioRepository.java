package com.copomex.copomex.repository;

import com.copomex.copomex.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    
}

