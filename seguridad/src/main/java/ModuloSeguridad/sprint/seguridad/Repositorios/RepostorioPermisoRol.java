package ModuloSeguridad.sprint.seguridad.Repositorios;

import ModuloSeguridad.sprint.seguridad.Modelos.PermisosRol;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepostorioPermisoRol extends MongoRepository<PermisosRol,String>{
}
