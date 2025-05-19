import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { BandaService, CancionService, CuentaService } from 'Frontend/generated/endpoints';

import Banda from 'Frontend/generated/com/practica02/base/models/Cancion';
import Cancion from 'Frontend/generated/com/practica02/base/models/Cuenta'

export const config: ViewConfig = {
  title: 'Cuenta',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Cuenta',
  },
};

type CuentaEntryFormProps = {
  onCuentaCreated?: () => void;
};

function CuentaEntryFormProps (props: CuentaEntryFormProps){
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const gmail = useSignal('');
  const clave = useSignal('');
  

  const createCuenta = async () => {
      try {
        if (gmail.value.trim().length > 0 &&
         clave.value.trim().length > 0) {
              
          await CuentaService.createCuenta(gmail.value, clave.value);
          if (props.onCuentaCreated) {
            props.onCuentaCreated();
          }
          gmail.value = '';
          clave.value = '';
          dialogOpened.value = false;
          Notification.show('Cuenta creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Cuenta"
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
            CREAR CUENTA
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCuenta}>
              Registrar Cuenta
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="CORREO" 
            placeholder="Ingrese el correo"
            aria-label='Correo'
            value={gmail.value}
            onValueChanged={(evt)=> (gmail.value = evt.detail.value)}
            />         
            
          </VerticalLayout>
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="CLAVE" 
            placeholder="Ingrese una clave"
            aria-label='Clave'
            value={clave.value}
            onValueChanged={(evt)=> (clave.value = evt.detail.value)}
            />           
            
          </VerticalLayout>
                   
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}


function link({item} : {item: Cancion}){
  return(
    <span>
      <Button>
        Editar 
      </Button>
    </span>
  )
}

function index({model}:{model:GridItemModel<Cancion>}) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

/*   private Integer id;
    private String gmail;
    private String clave;
    private Boolean estado;
    private String idPersona; */
export default function CuentaLisView() {
  const dataProvider = useDataProvider<Cancion>({
    list: () => CuentaService.listAllCuenta(),
  });

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Cuentas">
        <Group>
        <CuentaEntryFormProps onCuentaCreated={dataProvider.refresh} /> 
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
         <GridColumn renderer={index} header="Nro" />
        <GridColumn path="gmail" header="Gmail" />
        <GridColumn path="clave" header="Clave" >
       
         
        </GridColumn>
          <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}