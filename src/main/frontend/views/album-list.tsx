import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService } from 'Frontend/generated/endpoints';


import Album from 'Frontend/generated/com/practica02/base/models/Album';
import { createAlbum } from 'Frontend/generated/AlbumService';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Album',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Album',
  },
};

type AlbumEntryFormProps = {
  onAlbumCreated?: () => void;
};

//===============================================================
type AlbumEntryFormUpdateProps =  {
  onAlbumUpdateted?: () => void;
};

//===============================================================
// Función para convertir la fecha a un formato de entrada de fecha
function toDateInputValue(dateString: string | undefined): string {
  if (!dateString) return '';
  // Si ya es YYYY-MM-DD, devuélvelo tal cual
  if (/^\d{4}-\d{2}-\d{2}$/.test(dateString)) return dateString;
  // Si es un string ISO, conviértelo
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return '';
  const offset = date.getTimezoneOffset();
  const localDate = new Date(date.getTime() - (offset * 60 * 1000));
  return localDate.toISOString().slice(0, 10);
}


// Ingresar Album
function AlbumEntryFormProps (props: AlbumEntryFormProps){
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const fecha = useSignal(''); 

  const createAlbum = async () => {
      try {
        if (nombre.value.trim().length > 0 &&
         fecha.value.trim().length > 0) {
          await AlbumService.createAlbum(nombre.value, fecha.value);
          if (props.onAlbumCreated) {
            props.onAlbumCreated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Album creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Album"
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
            Agregar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createAlbum}>
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
            placeholder="Ingrese el nombre del album"
            aria-label='Nombre del album'
            value={nombre.value}
            onValueChanged={(evt)=> (nombre.value = evt.detail.value)}
            />
            <DatePicker
            label={'Fecha de creacion'}
            placeholder='Seleccione la fecha de creacion del album'
            aria-label='Fecha de creacion del album'
            value={fecha.value}
            onValueChanged={(evt)=> (fecha.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}
//===============================================================
// Actualizar Album
function AlbumEntryFormUpdate (props: AlbumEntryFormUpdateProps){
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const fecha = useSignal(toDateInputValue(props.arguments.fecha));
  const ident = useSignal(props.arguments.id); 

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    fecha.value = toDateInputValue(props.arguments.fecha);
    ident.value = props.arguments.id;
  }, [props.arguments]);

  const updateAlbum = async () => {
      try {
        if (nombre.value.trim().length > 0 &&
         fecha.value.trim().length > 0) {
          await AlbumService.updateAlbum(parseInt (ident.value), nombre.value, fecha.value);
          if (props.onAlbumUpdateted) {
            props.onAlbumUpdateted();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Album actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
            Actualizar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateAlbum}>
              Actualizar
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
            arial-label = 'Nombre de la banda'
            value={nombre.value}
            onValueChanged={(evt)=> (nombre.value = evt.detail.value)}
            />
            <DatePicker
            label={'Fecha de creacion'}
            placeholder='Seleccione la fecha de creacion de la banda'
            aria-label='Fecha de creacion de la banda'
            value={fecha.value}
            onValueChanged={(evt)=> (fecha.value = evt.detail.value)}
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

function index({model}:{model:GridItemModel<Album>}){
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

function fechaRenderer({ item }: { item: Album }) {
  return (
    <span>
      {item.fecha ? dateFormatter.format(new Date(item.fecha)) : ''}
    </span>
  );
}

export default function AlbumLisView() {
  const dataProvider = useDataProvider<Album>({
    list: () => AlbumService.listAllAlbum(),
  });

  function link({item} : {item: Album}){
  return(
    <span>
      <AlbumEntryFormUpdate arguments ={item} onAlbumUpdateted={dataProvider.refresh}/>
    </span>
  )
}

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Albums">
        <Group>
        <AlbumEntryFormProps onAlbumCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={index} header="Nro" />
        <GridColumn path="nombre" header="Nombre"/>
        <GridColumn renderer={fechaRenderer} header="Fecha">
        
        </GridColumn>
        <GridColumn header="Acciones" renderer={link} />
      </Grid>

    </main>
  );
}