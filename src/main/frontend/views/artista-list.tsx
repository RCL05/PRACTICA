import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService, ArtistaService, BandaService } from 'Frontend/generated/endpoints';

import Artista from 'Frontend/generated/com/practica02/base/models/Artista';
import { useEffect, useState } from 'react';
import RolArtistaEnum from 'Frontend/generated/com/practica02/base/models/RolArtistaEnum';

export const config: ViewConfig = {
  title: 'Artista',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Artista',
  },
};
//===============================================================
//Esta funcion sera un disparador de eventos
type ArtistaEntryFormProps = {
  onArtistaCreated?: () => void;
}

//update artista
type ArtistaEntryFormUpdateProps = {
  onArtistaUpdateted?: () => void;
};

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

//===============================================================
//GUARDAR ARTISTA
function ArtistaEntryForm(props: ArtistaEntryFormProps) {
  const [roles, setRoles] = useState<String[]>([]);
  const nombre = useSignal('');
  const nacionalidad = useSignal('');
  const apellido = useSignal('');
  const rolArtista = useSignal<RolArtistaEnum>();
  const imagen = useSignal('');


  const createArtista = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        nacionalidad.value.trim().length > 0 &&
        apellido.value.trim().length > 0) {
        await ArtistaService.createArtista(nombre.value, nacionalidad.value, apellido.value, rolArtista.value, imagen.value);
        if (props.onArtistaCreated) {
          props.onArtistaCreated();
        }
        nombre.value = '';
        nacionalidad.value = '';
        apellido.value = '';
        rolArtista.value = undefined;
        imagen.value = '';
        // Limpiar los campos del formulario
        dialogOpened.value = false;
        Notification.show('Artista creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  //ayuda a cargar los paiseas, y no que es un DAtaProvider(EL ING LO MENCIONO)
  //EN SU LUGAR SE UTILIZA UN STRING POR LA LISTA DE PAISES 

  let pais = useSignal<String[]>([]);
  useEffect(() => {
    ArtistaService.listCountry().then(data =>
      pais.value = data
    );
  }, []);


  //===========================================================================================
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo artista"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Candelar
            </Button>
            <Button onClick={createArtista} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del artista"
            placeholder="Ingrese el nombre del artista UWU"
            aria-label="Nombre del artista"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <TextField label="Apellidos del artista"
            placeholder='Ingrese el apellido del artista'
            aria-label='Apellido del artista'
            value={apellido.value}
            onValueChanged={(evt) => (apellido.value = evt.detail.value)}
          />
          <ComboBox label="Nacionalidad"
            items={pais.value}
            placeholder='Seleccione un pais'
            aria-label='Seleccione un pais de la lista'
            value={nacionalidad.value}
            onValueChanged={(evt) => (nacionalidad.value = evt.detail.value)}
          />
          <ComboBox label="Rol del artista"
            items={Object.values(RolArtistaEnum)}
            placeholder='Seleccione un rol'
            aria-label='Seleccione un rol de la lista'
            value={rolArtista.value}
            onValueChanged={(evt) => (rolArtista.value = evt.detail.value)}
          />
          <TextField
            label="URL de la imagen"
            placeholder="Ingrese la URL de la imagen"
            value={imagen.value}
            onValueChanged={(evt) => (imagen.value = evt.detail.value)}
          />

        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Agregar
      </Button>
    </>
  );
}
//===============================================================
//update artista
function BandaEntryFormUpdate(props: ArtistaEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const nacionalidad = useSignal(props.arguments.nacionalidad);
  const ident = useSignal(props.arguments.id);
  const apellido = useSignal(props.arguments.apellido);
  const rolArtista = useSignal<RolArtistaEnum>(props.arguments.rolArtista);

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    nacionalidad.value = props.arguments.nacionalidad;
    ident.value = props.arguments.id;
    apellido.value = props.arguments.apellido;
    rolArtista.value = props.arguments.rolArtista;
  }, [props.arguments]);

  const updateArtista = async () => {
    try {
      if (nombre.value.trim().length > 0 &&
        nacionalidad.value.trim().length > 0 && apellido.value.trim().length > 0) {
        await ArtistaService.updateArtista(parseInt(ident.value), nombre.value, nacionalidad.value, rolArtista.value, apellido.value);
        if (props.onArtistaUpdateted) {
          props.onArtistaUpdateted();
        }
        nombre.value = '';
        nacionalidad.value = '';
        apellido.value = '';
        rolArtista.value = undefined;
        dialogOpened.value = false;
        Notification.show('Artista actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  //ayuda a cargar los paiseas, y no que es un DAtaProvider(EL ING LO MENCIONO)
  let pais = useSignal<String[]>([]);
  useEffect(() => {
    ArtistaService.listCountry().then(data =>
      pais.value = data
    );
  }, []);


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
            Actualizar Artista
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateArtista}>
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
            <TextField label="Nombre del artista"
              placeholder="Ingrese el nombre del artista UWU"
              aria-label="Nombre del artista"
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Apellidos del artista"
              placeholder='Ingrese el apellido del artista'
              aria-label='Apellido del artista'
              value={apellido.value}
              onValueChanged={(evt) => (apellido.value = evt.detail.value)}
            />
            <ComboBox label="Nacionalidad"
              items={pais.value}
              placeholder='Seleccione un pais'
              aria-label='Seleccione un pais de la lista'
              value={nacionalidad.value}
              onValueChanged={(evt) => (nacionalidad.value = evt.detail.value)}
            />
            <ComboBox label="Rol del artista"
              items={Object.values(RolArtistaEnum)}
              placeholder='Seleccione un rol'
              aria-label='Seleccione un rol de la lista'
              value={rolArtista.value}
              onValueChanged={(evt) => (rolArtista.value = evt.detail.value)}
            />

          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}
//===============================================================
//LISTA de ARTISTAS
export default function ArtistaLisView() {
  const dataProvider = useDataProvider<Artista>({
    list: () => ArtistaService.listAll(),
  });

  function indexIndex({ model }: { model: GridItemModel<Artista> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  function link({ item }: { item: Artista }) {
    return (
      <span>
        <BandaEntryFormUpdate arguments={item} onArtistaUpdateted={dataProvider.refresh} />
      </span>
    )
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Artistas">
        <Group>
          <ArtistaEntryForm onArtistaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider} theme="row-stripes">
        {/*<GridColumn path="id" header="ID" />*/}
        <GridColumn renderer={indexIndex} header='Nro' />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="apellido" header="Apellido" />
        <GridColumn path="rolArtista" header="Rol" />
        <GridColumn path="nacionalidad" header="Nacionalidad" />
        <GridColumn
          header="Imagen"
          renderer={({ item }) => {
            return (
              <img
                src={item.imagen || 'https://via.placeholder.com/150'} // Imagen por defecto si no hay URL
                alt={`Imagen de ${item.nombre}`}
                style={{ width: '80px', height: '80px', objectFit: 'cover', borderRadius: '50%' }}
              />
            );
          }}
        >


        </GridColumn>
        <GridColumn renderer={link} header='Acciones' />
      </Grid>

    </main>
  );
}