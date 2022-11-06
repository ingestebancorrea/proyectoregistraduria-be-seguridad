package ModuloSeguridad.sprint.seguridad.Controladores;

// Imports SpringFramework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
// Imports project Models and Repositories
import ModuloSeguridad.sprint.seguridad.Modelos.Permiso;
import ModuloSeguridad.sprint.seguridad.Modelos.PermisosRoles;
import ModuloSeguridad.sprint.seguridad.Modelos.Rol;
import ModuloSeguridad.sprint.seguridad.Repositorios.RepositorioPermiso;
import ModuloSeguridad.sprint.seguridad.Repositorios.RepostorioPermisoRol;
import ModuloSeguridad.sprint.seguridad.Repositorios.RepositorioRol;
// Import Java Collections Framework
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisoRol {

    @Autowired
    private RepostorioPermisoRol miRepositorioPermisoRol;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisosRoles> index(){
        return this.miRepositorioPermisoRol.findAll();
    }

    /**
     * Asignación rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles create(@PathVariable String id_rol, @PathVariable String id_permiso){

        PermisosRoles nuevo=new PermisosRoles();
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();

        if (elRol != null && elPermiso != null){
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.miRepositorioPermisoRol.save(nuevo);
        } else{
            return null;
        }
    }
    @GetMapping("{id}")
    public PermisosRoles show(@PathVariable String id){
        PermisosRoles permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        return permisosRolesActual;
    }

    /**
     * Modificación Rol y Permiso
     * @param id
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRoles permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();
        if(permisosRolesActual != null && elPermiso != null && elRol != null){
            permisosRolesActual.setPermiso(elPermiso);
            permisosRolesActual.setRol(elRol);
            return this.miRepositorioPermisoRol.save(permisosRolesActual);
        } else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public  void delete(@PathVariable String id){
        PermisosRoles permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        if (permisosRolesActual!=null){
            this.miRepositorioPermisoRol.delete(permisosRolesActual);
        }
    }

    @GetMapping("validar-permiso/rol/{id_rol}") //implementación método validación de permisos
    public PermisosRoles getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso){
        Permiso permiso = miRepositorioPermiso.getPermiso(infoPermiso.getUrl(), infoPermiso.getMetodo());//obtener permiso
        Rol rol = miRepositorioRol.findById(id_rol).get();//obtener rol
        if(permiso !=null && rol !=null){
            return miRepositorioPermisoRol.getPermisosRoles(rol.get_id(), permiso.get_id());//devuelva lo del método
        }else{
            return null;
        }
    }
}