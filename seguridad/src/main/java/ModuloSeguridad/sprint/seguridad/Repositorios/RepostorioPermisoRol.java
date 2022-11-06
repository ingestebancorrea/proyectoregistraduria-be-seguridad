package ModuloSeguridad.sprint.seguridad.Repositorios;

import ModuloSeguridad.sprint.seguridad.Modelos.PermisosRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepostorioPermisoRol extends MongoRepository<PermisosRoles,String>{
    @Query("{'rol.$id': ObjectId(?0), 'permiso.$id': ObjectId(?1)}")
    PermisosRoles getPermisosRoles(String id_rol, String id_permiso);
}
