import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService, BandaService, PersonaService } from 'Frontend/generated/endpoints';

import Banda from 'Frontend/generated/com/practica02/base/models/Album';
import Album from 'Frontend/generated/com/practica02/base/models/Persona';
import Persona from 'Frontend/generated/com/practica02/base/models/Persona';

export const config: ViewConfig = {
  title: 'Persona',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Persona',
  },
};

type PersonaEntryFormProps = {
  onPersonaCreated?: () => void;
};

function PersonaEntryFormProps (props: PersonaEntryFormProps){
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };{/* <GridColumn path="creationDate" header="Creation Date">
    {({ item }) => dateTimeFormatter.format(new Date(item.creationDate))}
  </GridColumn> */}

  const close = () => {
    dialogOpened.value = false;
  };


  const usauario = useSignal('');  
  const edad = useSignal('');
  const createCancion = async () => {
      try {
        if (usauario.value.trim().length > 0 &&
         edad.value.toString().length > 0) {
          /*    public void createPersona(String usuario, Integer edad) throws Exception {
        if (usuario.trim().length() > 0 && edad.toString().length() > 0)*/
          await PersonaService.createPersona(usauario.value, parseInt(edad.value));
          if (props.onPersonaCreated) {
            props.onPersonaCreated();
          }
          usauario.value = '';
          edad.value = '';
          dialogOpened.value = false;
          Notification.show('Persona creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Persona"
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
            Agregar Persona
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
            <TextField label="Usuario" 
            placeholder="Ingrese el nombre de usuario"
            arial-label = 'Nombre de usuario'
            value={usauario.value}
            onValueChanged={(evt)=> (usauario.value = evt.detail.value)}
            />         
            
          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Edad" 
            placeholder="Ingrese su edad"
            arial-label = 'Edad'
            value={edad.value}
            onValueChanged={(evt)=> (edad.value = evt.detail.value)}
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

function link({item} : {item: Persona}){
  return(
    <span>
      <Button>
        Editar 
      </Button>
    </span>
  )
}

function index({model}:{model:GridItemModel<Persona>}) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

export default function PersonaLisView() {
  const dataProvider = useDataProvider<Persona>({
    list: () => PersonaService.listAllPersona(),
  });

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Personas">
        <Group>
        <PersonaEntryFormProps onPersonaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={index} header= "Nro"/>
        <GridColumn path="usauario" header="Usuario"/>
        <GridColumn path="edad" header="Edad">
                  
        </GridColumn>
        <GridColumn renderer={link} header="Acciones"/>
      </Grid>

    </main>
  );
}