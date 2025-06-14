import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { CancionService, GeneroService } from 'Frontend/generated/endpoints';

import { AlbumService } from 'Frontend/generated/endpoints';
import Album from 'Frontend/generated/com/practica02/base/models/Album';
import Cancion from 'Frontend/generated/com/practica02/base/models/Cancion';
import TipoArchivoEnum from 'Frontend/generated/com/practica02/base/models/TipoArchivoEnum';
import { useState, useEffect } from 'react';
import Genero from 'Frontend/generated/com/practica02/base/models/Genero';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Cancion',
  },
};


type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};
//===============================================================
type CancionEntryFormUpdateProps = {
  onCancionUpdateted?: () => void;
};
//listAllCancion

//===============================================================
// Ingrasar Cancion
function CancionEntryForm(props: CancionEntryFormProps) {



  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const url = useSignal('');
  const duracion = useSignal('');
  const tipo = useSignal();
  const album = useSignal('');
  const genero = useSignal('');


  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        url.value.trim().length > 0 &&
        duracion.value.toString().length > 0 &&
        album.value.toString().length > 0 &&
        genero.value.toString().length > 0
      ) {
        const id_genero = parseInt(genero.value) + 1;
        const id_album = parseInt(album.value) + 1;
        await CancionService.create(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = undefined;
        album.value = '';
        genero.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        aria-label="Registrar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Agregar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder="Ingrese el nombre de la cancion"
              arial-label='Nombre de la cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="URL"
              placeholder="Ingrese la URL de la canción"
              arial-label='URL de la canción'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Duración"
              placeholder="Ingrese la duración de la canción"
              arial-label='Duración de la canción'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <ComboBox label="Album"
              items={listaAlbum.value}
              placeholder='Seleccione un album'
              aria-label='Seleccione un album de la lista'
              value={album.value}
              onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
              items={listaGenero.value}
              placeholder='Seleccione un genero'
              aria-label='Seleccione un genero de la lista'
              value={genero.value}
              onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />

            <ComboBox label="Tipo de archivo"
              items={listaTipo.value}
              placeholder='Seleccione un tipo de archivo'
              aria-label='Seleccione un tipo de archivo de la lista'
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>

      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//===============================================================
//Actualizar Cancion
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };



  const nombre = useSignal(props.arguments.nombre);
  const url = useSignal(props.arguments.url);
  const duracion = useSignal(props.arguments.duracion.toString());
  const tipo = useSignal(props.arguments.tipo);
  const album = useSignal(props.arguments.id_albun);
  const genero = useSignal(props.arguments.id_genero);
  const ident = useSignal(props.arguments.id);

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    url.value = props.arguments.url;
    duracion.value = props.arguments.duracion.toString();
    tipo.value = props.arguments.tipo;
    album.value = props.arguments.id_albun;
    genero.value = props.arguments.id_genero;
    ident.value = props.arguments.id;
  }, [props.arguments]);

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        url.value.trim().length > 0 &&
        duracion.value.toString().length > 0 &&
        tipo.value &&
        album.value.toString().length > 0
        && genero.value.toString().length > 0
      ) {
        const id_genero = parseInt(genero.value) + 1;;
        const id_album = parseInt(album.value) + 1;
        await CancionService.update(parseInt(ident.value) - 1, nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionUpdateted) {
          props.onCancionUpdateted();
        }
        nombre.value = '';
        url.value = '';
        duracion.value = '';
        tipo.value = undefined;
        album.value = '';
        genero.value = '';
        dialogOpened.value = false;
        Notification.show('Canción actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        aria-label="Editar Banda"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Canción
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
            <TextField label="Nombre"
              placeholder="Ingrese el nombre de la canción"
              arial-label='Nombre de la canción'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />



            <TextField label="URL"
              placeholder="Ingrese la URL de la canción"
              arial-label='URL de la canción'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />


            <TextField label="Duración"
              placeholder="Ingrese la duración de la canción"
              arial-label='Duración de la canción'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <ComboBox label="Album"
              items={listaAlbum.value}
              placeholder='Seleccione un album'
              aria-label='Seleccione un album de la lista'
              value={album.value}
              onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
              items={listaGenero.value}
              placeholder='Seleccione un genero'
              aria-label='Seleccione un genero de la lista'
              value={genero.value}
              onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Tipo de archivo"
              items={listaTipo.value}
              placeholder='Seleccione un tipo de archivo'
              aria-label='Seleccione un tipo de archivo de la lista'
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />

          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}



const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});


export default function CancionLisView() {


  const [items, setItems] = useState([]);
  useEffect(() => {
    CancionService.listAllCancion().then((data) => {
      setItems(data);
    });
  }, []);
  const reloadCanciones = () => {
    CancionService.listAllCancion().then((data) => setItems(data));
  };

  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    console.log(`sorting by ${columnId} to ${direction}`);
    var dir = (direction == "asc") ? 1 : 2;
    if (direction === "original") {
      dir = 0; // o null, según tu backend
    } else if (direction === "asc") {
      dir = 1;
    } else {
      dir = 2;
    }
    CancionService.order(columnId, dir).then((data) => {
      setItems(data);
    });
  };


  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Nombre',
      value: 'nombre',
    },
    {
      label: 'Duración',
      value: 'duracion',
    },
    {
      label: 'Tipo',
      value: 'tipo',
    },
    {
      label: 'Album',
      value: 'albun',
    },
    {
      label: 'Genero',
      value: 'genero',
    },
    {
      label: 'Url',
      value: 'url',
    },
  ];
  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      CancionService.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  function link({ item }: { item: Cancion }) {
    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdateted={reloadCanciones} />
      </span>
    );
  }

  function index({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>

          <CancionEntryForm onCancionCreated={reloadCanciones} />
        </Group>
        <HorizontalLayout theme="spacing">
          <Select items={itemSelect}
            value={criterio.value}
            onValueChanged={(evt) => (criterio.value = evt.detail.value)}
            placeholder="Selecione un cirterio">


          </Select>

          <TextField
            placeholder="Search"
            style={{ width: '50%' }}
            value={texto.value}
            onValueChanged={(evt) => (texto.value = evt.detail.value)}
          >
            <Icon slot="prefix" icon="vaadin:search" />
          </TextField>
          <Button onClick={search} theme="primary">
            BUSCAR
          </Button>
        </HorizontalLayout>
      </ViewToolbar>
      <Grid items={items}>
        <GridColumn renderer={index} header="Nro" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "nombre")} path="nombre" header="Nombre" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "duracion")} path="duracion" header="Duración" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "tipo")} path="tipo" header="Tipo" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "albun")} path={'albun'} header="Nombre Album" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "genero")} path={'genero'} header="Nombre Genero" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "url")} path="url" header="Url" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}

/*

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};
//===============================================================
type CancionEntryFormUpdateProps = {
  onCancionUpdateted?: () => void;
};
//listAllCancion

//===============================================================
// Ingrasar Cancion
function CancionEntryFormProps(props: CancionEntryFormProps) {

  const [albumes, setAlbumes] = useState<Album[]>([]);
  const [generos, setGeneros] = useState<Genero[]>([]);

  useEffect(() => {
    AlbumService.listAllAlbum().then((data) => setAlbumes((data ?? []).filter(Boolean)));
    GeneroService.listAllGenero().then((data) => setGeneros((data ?? []).filter(Boolean)));
  }, []);
  
  
const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const url = useSignal('');
  const duracion = useSignal('');
  const tipo = useSignal<TipoArchivoEnum>();
  const id_album = useSignal('');
  const id_genero = useSignal('');


  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        url.value.trim().length > 0 &&
        duracion.value.toString().length > 0 &&
        id_album.value.toString().length > 0 &&
        id_genero.value.toString().length > 0
      ) {
        
        await CancionService.createCancion(nombre.value, parseInt(duracion.value), url.value, tipo.value, parseInt(id_album.value), parseInt(id_genero.value));
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = undefined;
        id_album.value = '';
        id_genero.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  return (
    <>
      <Dialog
        aria-label="Registrar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Agregar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder="Ingrese el nombre de la banda"
              arial-label='Nombre de la banda'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="URL"
              placeholder="Ingrese la URL de la canción"
              arial-label='URL de la canción'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />

          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Duración"
              placeholder="Ingrese la duración de la canción"
              arial-label='Duración de la canción'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <ComboBox
              label="Álbum"
              items={albumes}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_album.value}
              onValueChanged={(evt) => (id_album.value = evt.detail.value)}
              placeholder="Seleccione un álbum"
            />
            <ComboBox
              label="Genero"
              items={generos}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_genero.value}
              onValueChanged={(evt) => (id_genero.value = evt.detail.value)}
              placeholder="Seleccione un Genero"
            />  

            <ComboBox label="Tipo de archivo"
              items={Object.values(TipoArchivoEnum)}
              placeholder='Seleccione un tipo de archivo'
              aria-label='Seleccione un tipo de archivo de la lista'
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//===============================================================
//Actualizar Cancion
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {
  const [albumes, setAlbumes] = useState<Album[]>([]);
  const [generos, setGeneros] = useState<Genero[]>([]);

  useEffect(() => {
    AlbumService.listAllAlbum().then((data) => setAlbumes((data ?? []).filter(Boolean)));
    GeneroService.listAllGenero().then((data) => setGeneros((data ?? []).filter(Boolean)));  
  }, []);

  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };



  const nombre = useSignal(props.arguments.nombre);
  const url = useSignal(props.arguments.url);
  const duracion = useSignal(props.arguments.duracion.toString());
  const tipo = useSignal<TipoArchivoEnum>(props.arguments.tipo);
  const id_album = useSignal(Number(props.arguments.id_album));
  const id_genero = useSignal(Number(props.arguments.id_genero));
  const ident = useSignal(props.arguments.id);

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    url.value = props.arguments.url;
    duracion.value = props.arguments.duracion.toString();
    tipo.value = props.arguments.tipo;
    id_album.value = Number(props.arguments.id_album);
    id_genero.value = Number(props.arguments.id_genero);
    ident.value = props.arguments.id;
  }, [props.arguments]);

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        url.value.trim().length > 0 &&
        duracion.value.toString().length > 0 &&
        tipo.value &&
        id_album.value.toString().length > 0 &&
        id_genero.value.toString().length > 0) {
        await CancionService.updateCancion(parseInt(ident.value), nombre.value, parseInt(duracion.value), url.value, tipo.value, parseInt(id_album.value), parseInt(id_genero.value));
        if (props.onCancionUpdateted) {
          props.onCancionUpdateted();
        }
        nombre.value = '';
        url.value = '';
        duracion.value = '';
        tipo.value = undefined;
        id_album.value = '';
        id_genero.value = '';
        dialogOpened.value = false;
        Notification.show('Canción actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  return (
    <>
      <Dialog
        aria-label="Editar Banda"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Canción
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
            <TextField label="Nombre"
              placeholder="Ingrese el nombre de la canción"
              arial-label='Nombre de la canción'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />



            <TextField label="URL"
              placeholder="Ingrese la URL de la canción"
              arial-label='URL de la canción'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />


            <TextField label="Duración"
              placeholder="Ingrese la duración de la canción"
              arial-label='Duración de la canción'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <ComboBox
              label="Álbum"
              items={albumes}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_album.value}
              onValueChanged={(evt) => (id_album.value = evt.detail.value)}
              placeholder="Seleccione un álbum"
            />
            <ComboBox
              label="Genero"
              items={generos}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_genero.value}
              onValueChanged={(evt) => (id_genero.value = evt.detail.value)}
              placeholder="Seleccione un Genero"
            />
            <ComboBox label="Tipo de archivo"
              items={Object.values(TipoArchivoEnum)}
              placeholder='Seleccione un tipo de archivo'
              aria-label='Seleccione un tipo de archivo de la lista'
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />

          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}



const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});


export default function CancionLisView() {
  const [albumes, setAlbumes] = useState<Album[]>([]);
  const [generos, setGeneros] = useState<Genero[]>([]);

  useEffect(() => {
    AlbumService.listAllAlbum().then((data) => setAlbumes((data ?? []).filter(Boolean)));
    GeneroService.listAllGenero().then((data) => setGeneros((data ?? []).filter(Boolean)));
  }, []);
  const dataProvider = useDataProvider<Cancion>({
    list: () => CancionService.listAll(),
  });

  function link({ item }: { item: Cancion }) {
    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdateted={dataProvider.refresh} />
      </span>
    )
  }

  function index({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>
          <CancionEntryFormProps onCancionCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={index} header="Nro" />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="duracion" header="Duración" />
        <GridColumn path="tipo" header="Tipo" />
        <GridColumn path={'nombre_album'} header="Nombre Album" />
        <GridColumn path={'nombre_genero'} header="Nombre Genero" />  
        <GridColumn path="url" header="Url" >


        </GridColumn>
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}
*/