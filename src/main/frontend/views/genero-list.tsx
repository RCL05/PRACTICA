import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService, BandaService, GeneroService } from 'Frontend/generated/endpoints';

import Genero from 'Frontend/generated/com/practica02/base/models/Genero';
import { createGenero } from 'Frontend/generated/GeneroService';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Genero',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: '  Genero',
  },
};

type GeneroEntryFormProps = {
  onGeneroCreated?: () => void;
};
type GeneroEntryFormUpdateProps = {
  onGeneroUpdateted?: () => void;
};
//===============================================================

function GeneroEntryFormProps(props: GeneroEntryFormProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');

  const createGenero = async () => {
    try {
      if (nombre.value.trim().length > 0) {

        await GeneroService.createGenero(nombre.value);
        if (props.onGeneroCreated) {
          props.onGeneroCreated();
        }
        nombre.value = '';
        dialogOpened.value = false;
        Notification.show('Genero creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Genero"
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
            <Button theme="primary" onClick={createGenero}>
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
            <TextField label="Nombre De Genero"
              placeholder="Ingrese el nombre de genero"
              aria-label='Nombre de genero'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}


function GeneroEntryFormUpdate(props: GeneroEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const ident = useSignal(props.arguments.id);
  useEffect(() => {
    nombre.value = props.arguments.nombre;
  }, [props.arguments]);

  const updateGenero = async () => {
    try {
      if (nombre.value.trim().length > 0) {
        await GeneroService.updateGenero(parseInt(ident.value), nombre.value);
        if (props.onGeneroUpdateted) {
          props.onGeneroUpdateted();
        }
        nombre.value = '';
        dialogOpened.value = false;
        Notification.show('Genero actualizado', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Editar Genero"
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
            <Button theme="primary" onClick={updateGenero}>
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
            <TextField label="Nombre De Genero"
              placeholder="Ingrese el nombre de genero"
              aria-label='Nombre de genero'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}



const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});


function index({ model }: { model: GridItemModel<Genero> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

export default function GeneroLisView() {
  const dataProvider = useDataProvider<Genero>({
    list: () => GeneroService.listAllGenero(),
  });
  function link({ item }: { item: Genero }) {
    return (
      <span>
        <GeneroEntryFormUpdate arguments={item} onGeneroUpdateted={dataProvider.refresh} />
      </span>
    )
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Generos">
        <Group>
          <GeneroEntryFormProps onGeneroCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={index} header="Nro" />
        <GridColumn path="nombre" header="Nombre">


        </GridColumn>
        <GridColumn header="Acciones" renderer={link} />
      </Grid>

    </main>
  );

}