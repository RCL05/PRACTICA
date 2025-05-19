import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextArea, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { BandaService } from 'Frontend/generated/endpoints';

import Banda from 'Frontend/generated/com/practica02/base/models/Banda';
import { data } from 'react-router';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Banda',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Banda',
  },
};

type BandaEntryFormProps = {
  onBandaCreated?: () => void;
};
//===============================================================
type BandaEntryFormUpdateProps =  {
  onBandaUpdateted?: () => void;
};

// Ingresar Banda
function BandaEntryFormProps (props: BandaEntryFormProps){
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const fecha = useSignal(''); 

  const createBanda = async () => {
      try {
        if (nombre.value.trim().length > 0 &&
         fecha.value.trim().length > 0) {
          await BandaService.createBanda(nombre.value, fecha.value);
          if (props.onBandaCreated) {
            props.onBandaCreated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Banda creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Banda"
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
            Agregar Banda
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createBanda}>
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
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//===============================================================
//valor para el DatePicker sea YYYY-MM-DD, Agrega una función para formatear la fecha correctamente antes de asignarla al campo:
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


//update data Banda
function BandaEntryFormUpdate (props: BandaEntryFormUpdateProps){
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
  // Asignar el valor de la fecha formateada al campo de fecha`
  useEffect(() => {
  nombre.value = props.arguments.nombre;
  fecha.value = toDateInputValue(props.arguments.fecha);
}, [props.arguments]);

  const updateBanda = async () => {
      try {
        if (nombre.value.trim().length > 0 &&
         fecha.value.trim().length > 0) {
          await BandaService.updateBanda(parseInt (ident.value), nombre.value, fecha.value);
          if (props.onBandaUpdateted) {
            props.onBandaUpdateted();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Banda actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
            Add note
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateBanda}>
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

function index({model}:{model:GridItemModel<Banda>}) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}
function fechaRenderer({ item }: { item: Banda }) {
  return (
    <span>
      {item.fecha ? dateFormatter.format(new Date(item.fecha)) : ''}
    </span>
  );
}

export default function BandaLisView() {
  const dataProvider = useDataProvider<Banda>({
    list: () => BandaService.listAllBanda(),
  });
  function link({item} : {item: Banda}){
  return(
    <span>
      <BandaEntryFormUpdate arguments ={item} onBandaUpdateted={dataProvider.refresh}/>
    </span>
  )
}


  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Bandas">
        <Group>
         <BandaEntryFormProps onBandaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
      <GridColumn renderer={index} header = "Nro"/>
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn renderer={fechaRenderer} header="Fecha">
          
        </GridColumn>
        <GridColumn header = "Acciones" renderer={link}/>
      </Grid>
    </main>
  );
}



