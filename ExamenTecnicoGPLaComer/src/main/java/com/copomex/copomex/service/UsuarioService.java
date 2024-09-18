package com.copomex.copomex.service;

import com.copomex.copomex.entities.Usuario;
import com.copomex.copomex.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
   public List<Usuario> findAll(){
       return usuarioRepository.findAll();
   }
   
   public Usuario findById(Long Id){
       return usuarioRepository.findById(Id).orElse(null);
   }
   
   public Usuario save(Usuario usuario){
       return usuarioRepository.save(usuario);
   }
   
   public void delete(Long  id){
       usuarioRepository.deleteById(id);
   }
   
}
