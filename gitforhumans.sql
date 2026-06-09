CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    avatar_url TEXT DEFAULT '/assets/img/default_avatar.png',
    is_active BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repositorios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    ruta_local_git VARCHAR(255) NOT NULL,
    id_creador UUID NOT NULL,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_repositorio_creador FOREIGN KEY (id_creador) 
        REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE colaboradores (
    id_usuario UUID NOT NULL,
    id_repositorio UUID NOT NULL,
    nivel_permiso VARCHAR(20) NOT NULL,
    fecha_asignacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_repositorio),
    CONSTRAINT fk_colaborador_usuario FOREIGN KEY (id_usuario) 
        REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_colaborador_repositorio FOREIGN KEY (id_repositorio) 
        REFERENCES repositorios(id) ON DELETE CASCADE
);

CREATE TABLE archivos_metadata (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_repositorio UUID NOT NULL,
    id_usuario_subio UUID NOT NULL,
    nombre_archivo VARCHAR(255) NOT NULL,
    ruta_almacenamiento TEXT NOT NULL,
    tamanio_bytes BIGINT NOT NULL DEFAULT 0,
    tipo_cambio VARCHAR(30) NOT NULL,
    version_codigo INT NOT NULL DEFAULT 1,
    fecha_subida TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_archivo_repositorio FOREIGN KEY (id_repositorio) 
        REFERENCES repositorios(id) ON DELETE CASCADE,
    CONSTRAINT fk_archivo_usuario FOREIGN KEY (id_usuario_subio)
        REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE human_diffs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_archivo UUID NOT NULL,
    id_usuario UUID NOT NULL, 
    descripcion_vineta TEXT NOT NULL,
    fecha_commit TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_diff_archivo FOREIGN KEY (id_archivo) 
        REFERENCES archivos_metadata(id) ON DELETE CASCADE,
    CONSTRAINT fk_diff_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id) ON DELETE SET NULL
);