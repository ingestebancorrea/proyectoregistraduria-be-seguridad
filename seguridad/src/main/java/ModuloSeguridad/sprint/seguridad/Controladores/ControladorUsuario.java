package ModuloSeguridad.sprint.seguridad.Controladores;

import ModuloSeguridad.sprint.seguridad.Modelos.Rol;
import ModuloSeguridad.sprint.seguridad.Modelos.Usuario;
import ModuloSeguridad.sprint.seguridad.Repositorios.RepositorioRol;
import ModuloSeguridad.sprint.seguridad.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;
    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping(" ")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }
    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        return usuarioActual;
    }
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody Usuario
            infoUsuario){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()))
            ;
            return this.miRepositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }
    }

    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id, @PathVariable
    String id_rol) {
        Usuario usuarioActual = this.miRepositorioUsuario.findById(id).orElseThrow(RuntimeException::new);
        Rol rolActual = this.miRepositorioRol.findById(id_rol).orElseThrow(RuntimeException::new);
        usuarioActual.setRol(rolActual);
        return this.miRepositorioUsuario.save(usuarioActual);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual=this.miRepositorioUsuario.findById(id).orElse(null);
        if (usuarioActual!=null){
            this.miRepositorioUsuario.delete(usuarioActual);
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @PostMapping("/validate") //Validación de usuario (email, contraseña ) en DB
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException {
        Usuario usuario = miRepositorioUsuario.getUserByEmail(infoUsuario.getCorreo());
        String clave_ingresada = convertirSHA256(infoUsuario.getContrasena());//captura cadena con variable
        if( usuario != null){
            if(usuario.getContrasena().equals(clave_ingresada)){
                usuario.setContrasena("");
                return usuario;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }
}
