package com.copomex.copomex.controller;

import com.copomex.copomex.entities.CopomexResponse;
import com.copomex.copomex.entities.Usuario;
import com.copomex.copomex.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioService.findAll();
    }
    
    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.findById(id);
    }
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        List<CopomexResponse> direcciones = obtenerDireccionDesdeCOPOMEX(usuario.getCodigoPostal());
  
            if (direcciones != null && !direcciones.isEmpty()) {
                    CopomexResponse direccion = direcciones.get(0);
                    String direccionCompleta = 
                    String.join(" , ", direccion.getResponse().getAsentamiento(), direccion.getResponse().getMunicipio(), direccion.getResponse().getEstado(), direccion.getResponse().getPais());
                    usuario.setDireccion(direccionCompleta);
            } else {
                     usuario.setDireccion("Dirección no encontrada");
            }
    
            return usuarioService.save(usuario);
      }
    
    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        usuario.setId(id);
        
        List<CopomexResponse> direcciones = obtenerDireccionDesdeCOPOMEX(usuario.getCodigoPostal());
        if (direcciones != null && !direcciones.isEmpty()) {
                    CopomexResponse direccion = direcciones.get(0);
                    String direccionCompleta = 
                    String.join(" , ", direccion.getResponse().getAsentamiento(), direccion.getResponse().getMunicipio(), direccion.getResponse().getEstado(), direccion.getResponse().getPais());
                    usuario.setDireccion(direccionCompleta);
            } else {
                     usuario.setDireccion("Dirección no encontrada");
            }
        return usuarioService.save(usuario);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id){
        usuarioService.delete(id);
    }
    
    
    @Autowired
    private RestTemplate restTemplate;
    
    private List<CopomexResponse> obtenerDireccionDesdeCOPOMEX(String codigoPostal) { 
            String url = "https://api.copomex.com/query/info_cp/" + codigoPostal + "?token=63fa7e76-1697-42fc-b9fa-dc4b9d53e2dd";
    
            ResponseEntity<List<CopomexResponse>> responseEntity =
                      restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CopomexResponse>>() {});
    
             if (responseEntity.getStatusCode() == HttpStatus.OK) {
                         return responseEntity.getBody();
                }
                
             return null;

    }
    
}
