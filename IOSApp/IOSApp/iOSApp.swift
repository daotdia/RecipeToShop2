import SwiftUI
import sharedApp

@main
struct iOSApp: App {
    
    private let cacheModule: CacheModule
    private let caseUses: UseCases
    
    //Inicializo la base de datos y los casos de uso que se utilizarán en toda la APP.
    init(){
        self.cacheModule = CacheModule()
        self.caseUses = UseCases(cacheModule: self.cacheModule)
    }
    //Estado auxiliar para controlar la navgación.
    @State var tabSelection = 1
    
    //Las flags necesarias para gestionar diaolgos e iconos dinámicos
    @State var openDeleteIcon: Bool = false
    @State var openDialogDelete: Bool = false
    @State var openDialogListaRecetas: Bool = false
    
	var body: some Scene {
		WindowGroup {
            //La navegación se realiza mediante menú inferior.
            TabView{
                //Pantalla de despensa.
                NavigationView {
                    Despensa(
                        caseUses: self.caseUses,
                        openDeleteIcon: $openDeleteIcon,
                        openDialogDelete: $openDialogDelete
                    )
                        .navigationTitle("Despensa")
                }
                .tabItem(){
                    //TODO: Poner las imagenes correctas de los iconos de los menus.
                    Image(systemName: "heart.fill")
                    Text("Despensa")
                }
                .tag(1)
                
                //Pantalla de lista de recetas.
                NavigationView{
                    ListaRecetasScreen(
                        caseUses: self.caseUses,
                        openDialog:$openDialogListaRecetas,
                        tabSelection: $tabSelection
                    )
                    .navigationTitle("Lista de recetas")

                }
                .tabItem(){
                    //TODO: Poner las imagenes correctas de los iconos de los menus.
                    Image(systemName: "heart.fill")
                    Text("Recetas")
                }
                .tag(2)
                
            
                //TODO: Crear screen de compra.
                NavigationView{
                    Text("Compra")
                        .navigationTitle("Lista de la compra")
                }
                .tabItem(){
                    //TODO: Poner las imagenes correctas de los iconos de los menus.
                    Image(systemName: "heart.fill")
                    Text("Compra")
                }
                .tag(3)
            }
            .onTapGesture(count: 2, perform: {
                //Aquí reinicio todas las flags que deben de reiniciarse en el caso de que el usuario cambie de foco.
                openDeleteIcon = false
                openDialogDelete = false
                openDialogListaRecetas = false
            })
        }
    }
}
