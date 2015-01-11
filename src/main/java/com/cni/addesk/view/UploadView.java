package com.cni.addesk.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.util.Messages;
import com.vaadin.data.Item;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.StreamVariable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;


public class UploadView extends CustomComponent implements View{
	
	public static final String NAME = Messages.getString("UploadView.uploadViewName"); //$NON-NLS-1$
	private final AbstractOrderedLayout rootVerticalLayout;
	private View oldView;
	private static final String FILE_UPLOAD_LOACTION = Messages.getString("UploadView.fileUploadLocation"); //$NON-NLS-1$
	private final Table filesTable;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute(Messages.getString("UploadView.user"), null); //$NON-NLS-1$

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);  
	    		
	    	}
	    }  
	};
	
	public UploadView(){
		//setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
	    rootVerticalLayout.setSizeFull();
	    //rootVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout(Messages.getString("UploadView.header")); //$NON-NLS-1$
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem(Messages.getString("UploadView.empty"), new ThemeResource(Messages.getString("UploadView.menuItem")), null); //$NON-NLS-1$ //$NON-NLS-2$
		menuItem.addItem(Messages.getString("UploadView.homeMenuItem"), null, menuCommand); //$NON-NLS-1$
		menuItem.addItem(Messages.getString("UploadView.reportsMenuItem"), null, menuCommand); //$NON-NLS-1$
		menuItem.addItem(Messages.getString("UploadView.historyMenuItem"), null, menuCommand); //$NON-NLS-1$
		menuItem.addItem(Messages.getString("UploadView.helpInfoMenuItem"), null, menuCommand); //$NON-NLS-1$
		menuItem.addItem(Messages.getString("UploadView.signOutMenuItem"), null, menuCommand); //$NON-NLS-1$
		
		Label logoLabel = new Label(Messages.getString("UploadView.logoLabel"), ContentMode.HTML); //$NON-NLS-1$
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem(Messages.getString("UploadView.homeMenuItem"), null, menuCommand);         //$NON-NLS-1$
		MenuItem helpMenuItem = rightMenu.addItem(Messages.getString("UploadView.helpMenuItem"), null, menuCommand); //$NON-NLS-1$
		MenuItem myaccountMenuItem = rightMenu.addItem(Messages.getString("UploadView.myaccountMenuItem"), null, null); //$NON-NLS-1$
		myaccountMenuItem.addItem(Messages.getString("UploadView.changePasswordMenuItem"), null, menuCommand); //$NON-NLS-1$
		
		headerTemplate.addComponent(menubar, Messages.getString("UploadView.menubar")); //$NON-NLS-1$
		headerTemplate.addComponent(logoLabel, Messages.getString(Messages.getString("UploadView.logo")));   //$NON-NLS-1$
		headerTemplate.addComponent(rightMenu, Messages.getString("UploadView.rightMenu")); //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout uploadHomeHorizontalLayout = new HorizontalLayout();
		uploadHomeHorizontalLayout.setSpacing(true);
		uploadHomeHorizontalLayout.setWidth(Messages.getString("UploadView.uploadHomeHorizontalLayoutWidth")); //$NON-NLS-1$
		uploadHomeHorizontalLayout.setHeight(Messages.getString("UploadView.uploadHomeHorizontalLayoutHeight")); //$NON-NLS-1$
		uploadHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label uploadHomeLabel = new Label(Messages.getString("UploadView.uploadHomeLabel"), ContentMode.HTML); //$NON-NLS-1$
		uploadHomeHorizontalLayout.addComponent(uploadHomeLabel);
		uploadHomeHorizontalLayout.setComponentAlignment(uploadHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label(Messages.getString("UploadView.welcomeLabel"), ContentMode.HTML); //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label(Messages.getString("UploadView.usernameLabel")); //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource(Messages.getString("UploadView.companyLogoImage"))); //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		uploadHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		uploadHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(uploadHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label(Messages.getString("UploadView.pageTitleLineLabel"), ContentMode.HTML); //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout creativeInfoHorizontalLayout = new HorizontalLayout();
		creativeInfoHorizontalLayout.setSpacing(true);
		creativeInfoHorizontalLayout.setWidth(Messages.getString("UploadView.creativeInfoHorizontalLayoutWidth")); //$NON-NLS-1$
		creativeInfoHorizontalLayout.setHeight(Messages.getString("UploadView.creativeInfoHorizontalLayoutHeight")); //$NON-NLS-1$
		creativeInfoHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(creativeInfoHorizontalLayout);		
		
		Label  creativeInfoLabel  = new Label(Messages.getString("UploadView.creativeInfoLabel"), ContentMode.HTML); //$NON-NLS-1$
		
		creativeInfoHorizontalLayout.addComponent(creativeInfoLabel);
		
		Label  contactLineLabel  = new Label(Messages.getString("UploadView.contactLineLabel"), ContentMode.HTML); //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(contactLineLabel);
		
		Label adCreativeLabel = new Label(Messages.getString("UploadView.adCreativeLabel"), ContentMode.HTML); //$NON-NLS-1$
			
		AbstractOrderedLayout adCreativeVerticalLayout = new VerticalLayout();
		adCreativeVerticalLayout.setSpacing(true);
		adCreativeVerticalLayout.setSizeFull();
		adCreativeVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		adCreativeVerticalLayout.addComponent(adCreativeLabel);
		
		rootVerticalLayout.addComponent(adCreativeVerticalLayout);
		
		AbstractOrderedLayout instructionsHorizontalLayout = new HorizontalLayout();
		instructionsHorizontalLayout.setSpacing(true);
		
		AbstractOrderedLayout instructionsVerticalLayout = new VerticalLayout();
		instructionsVerticalLayout.setSpacing(true);
		
		ComboBox typesOfFilesComboBox = new ComboBox(Messages.getString("UploadView.typesOfFilesComboBoxText")); //$NON-NLS-1$
		typesOfFilesComboBox.setWidth(Messages.getString("UploadView.typesOfFilesComboBoxWidth")); //$NON-NLS-1$
		typesOfFilesComboBox.setInvalidAllowed(false);
		typesOfFilesComboBox.addItem(Messages.getString("UploadView.typesOfFilesComboBoxItem1")); //$NON-NLS-1$
		
		TextField descriptionTextField = new TextField(Messages.getString("UploadView.descriptionTextFieldText")); //$NON-NLS-1$
		descriptionTextField.setWidth(Messages.getString("UploadView.descriptionTextFieldWidth")); //$NON-NLS-1$
		descriptionTextField.setInputPrompt(Messages.getString("UploadView.descriptionTextFieldInputPrompt")); //$NON-NLS-1$
		
		instructionsVerticalLayout.addComponent(typesOfFilesComboBox);
		instructionsVerticalLayout.addComponent(descriptionTextField);
		
		instructionsHorizontalLayout.addComponent(instructionsVerticalLayout);
		
		TextArea instructionsTextArea = new TextArea(Messages.getString("UploadView.instructionsTextAreaText")); //$NON-NLS-1$
		instructionsTextArea.setWidth(Messages.getString("UploadView.instructionsTextAreaWidth")); //$NON-NLS-1$
		instructionsTextArea.setHeight(Messages.getString("UploadView.instructionsTextAreaHeight")); //$NON-NLS-1$
		instructionsTextArea.setInputPrompt(Messages.getString("UploadView.instructionsTextAreaInputPrompt")); //$NON-NLS-1$
		
		instructionsHorizontalLayout.addComponent(instructionsTextArea);
		
		adCreativeVerticalLayout.addComponent(instructionsHorizontalLayout);
		
		Label addFilesLabel = new Label(Messages.getString("UploadView.addFilesLabel"), ContentMode.HTML); //$NON-NLS-1$
			
		adCreativeVerticalLayout.addComponent(addFilesLabel);
		
		uploadCreative((VerticalLayout)adCreativeVerticalLayout);
		
		filesTable = new Table();
		filesTable.setHeight(Messages.getString("UploadView.filesTableHeight")); //$NON-NLS-1$
		filesTable.addContainerProperty(Messages.getString("UploadView.filesTableHeightColumn1"), String.class, null); //$NON-NLS-1$
		filesTable.addContainerProperty(Messages.getString("UploadView.filesTableHeightColumn2"), TextField.class, null); //$NON-NLS-1$
		filesTable.addContainerProperty(Messages.getString("UploadView.filesTableHeightColumn3"), ComboBox.class, null); //$NON-NLS-1$
		filesTable.addGeneratedColumn(Messages.getString("UploadView.filesTableHeightColumn4"), new ColumnGenerator() { //$NON-NLS-1$
		@Override 
		public Object generateCell(final Table source, final Object itemId, Object columnId) { 
			Button button = new Button(Messages.getString("UploadView.filesTableDeleteButton"));  //$NON-NLS-1$
			button.addClickListener(new ClickListener() { 
				@Override 
				public void buttonClick(ClickEvent event) {
					Item item = source.getContainerDataSource().getItem(itemId);
					Object uploadedFileProperty = item.getItemProperty(Messages.getString("UploadView.filesTableHeightColumn1")).getValue(); //$NON-NLS-1$
					File fileToDelete = new File(FILE_UPLOAD_LOACTION + String.valueOf(uploadedFileProperty));
					fileToDelete.delete();
					source.getContainerDataSource().removeItem(itemId);
					}
				});
			return button;
			}
		});
		
		adCreativeVerticalLayout.addComponent(filesTable);
		
		displayUploadedCreatives(filesTable);
		
		AbstractOrderedLayout readAgreeHorizontalLayout = new HorizontalLayout();
		
		ClickableLabel termsOfUseLabel = new ClickableLabel(Messages.getString("UploadView.termsOfUseLabel"));   //$NON-NLS-1$
		
		CheckBox readAgreeCheckbox = new CheckBox();
		
		readAgreeHorizontalLayout.addComponent(readAgreeCheckbox);
		readAgreeHorizontalLayout.addComponent(termsOfUseLabel);
		
		adCreativeVerticalLayout.addComponent(readAgreeHorizontalLayout);
		
		Button submitButton = new Button(Messages.getString("UploadView.submitButtonText"));	  //$NON-NLS-1$
		submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            }
        });
		
		Button cancelButton = new Button(Messages.getString("UploadView.cancelButtonText"));  //$NON-NLS-1$
		cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	if(oldView instanceof MainView){
    	            getUI().getNavigator().navigateTo(MainView.NAME);
	            }else if(oldView instanceof InformationView){
	            	getUI().getNavigator().navigateTo(InformationView.NAME);
	            }
            }
        });		
		
		AbstractOrderedLayout buttonsHorizontalLayout = new HorizontalLayout(submitButton, cancelButton);
		buttonsHorizontalLayout.setSizeUndefined();
		buttonsHorizontalLayout.setSpacing(true);
		buttonsHorizontalLayout.setMargin(new MarginInfo(true, false, true, false));
		
		adCreativeVerticalLayout.addComponent(buttonsHorizontalLayout);       
		
	}
	
	final private void displayUploadedCreatives(Table filesTable){
		
		if(filesTable.size() > 0){
			filesTable.removeAllItems();
		}
		
		ComboBox typeComboBox = null;
        
        TextField instructionsCopyTextField = null;
        
        File creativeFiles = new File(FILE_UPLOAD_LOACTION);
        
        String[] creativeList = creativeFiles.list();
        
        List<Object> uploadedCreativeList = null;
        for(String creativeName : creativeList){
        	uploadedCreativeList = new CopyOnWriteArrayList<Object>();
        	uploadedCreativeList.add(creativeName);
        	instructionsCopyTextField= new TextField();
            instructionsCopyTextField.setWidth(Messages.getString("UploadView.instructionsCopyTextFieldWidth")); //$NON-NLS-1$
        	uploadedCreativeList.add(instructionsCopyTextField);
        	typeComboBox = new ComboBox();
            typeComboBox.setInvalidAllowed(false);
            typeComboBox.setInputPrompt(Messages.getString("UploadView.typeComboBoxInputPrompt")); //$NON-NLS-1$
            typeComboBox.addItem(Messages.getString("UploadView.typeComboBoxItem1")); //$NON-NLS-1$
            typeComboBox.addItem(Messages.getString("UploadView.typeComboBoxItem2")); //$NON-NLS-1$
            typeComboBox.addItem(Messages.getString("UploadView.typeComboBoxItem3")); //$NON-NLS-1$
        	uploadedCreativeList.add(typeComboBox);    		
    		Object[] uploadedCreativeArray = uploadedCreativeList.toArray(new Object[uploadedCreativeList.size()]);
    		filesTable.addItem(uploadedCreativeArray, null);
        }		
		
	}
	
	private void uploadCreative(VerticalLayout layout) {
        // Show uploaded file in this placeholder
        //final Image image = new Image("Uploaded Image");
        //image.setVisible(false);

        // Implement both receiver that saves upload in a file and
        // listener for successful upload
        class ImageReceiver implements Receiver, SucceededListener {

            public File file;
           
            public OutputStream receiveUpload(String filename, String mimeType) {
                // Create upload stream
                FileOutputStream fos = null; // Stream to write to
                try {
                    // Open the file for writing.
                    file = new File(FILE_UPLOAD_LOACTION + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification(Messages.getString("UploadView.notification1"), e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent()); //$NON-NLS-1$
                    return null;
                }
                return fos; // Return the output stream to write to
            }

            public void uploadSucceeded(SucceededEvent event) {
                //Show the uploaded file in the image viewer
                //image.setVisible(true);
                //image.setSource(new FileResource(file));
            	displayUploadedCreatives(filesTable);
            }
        };
        ImageReceiver receiver = new ImageReceiver(); 

        // Create the upload with a caption and set receiver later
        final Upload upload = new Upload(null, receiver);
        upload.setImmediate(true);
        upload.setButtonCaption(Messages.getString("UploadView.uploadButtonCaption")); //$NON-NLS-1$
        upload.addSucceededListener(receiver);
       
        // Prevent too big downloads
        final long UPLOAD_LIMIT = 2 * 1024 * 1024; // 2MB
        upload.addStartedListener(new StartedListener() {
            @Override
            public void uploadStarted(StartedEvent event) {
                if (event.getContentLength() > UPLOAD_LIMIT) {
                    Notification.show(Messages.getString("UploadView.notification2"), Notification.Type.ERROR_MESSAGE); //$NON-NLS-1$
                    upload.interruptUpload();
                }
            }
        });
       
        // Check the size also during progress
        upload.addProgressListener(new ProgressListener() {

            @Override
            public void updateProgress(long readBytes, long contentLength) {
                if (readBytes > UPLOAD_LIMIT) {
                    Notification.show(Messages.getString("UploadView.notification2"), Notification.Type.ERROR_MESSAGE); //$NON-NLS-1$
                    upload.interruptUpload();
                }
            } 
        });
        
        //absLayout.addComponent(upload);
        DragAndDropWrapper uploadWrapper = new DragAndDropWrapper(upload);
        
        // Handle moving components within the AbsoluteLayout
        uploadWrapper.setDropHandler(new DropHandler() {
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
            
            public void drop(DragAndDropEvent event) {
            	// expecting this to be an html5 drag
                WrapperTransferable tr = (WrapperTransferable) event.getTransferable();
                Html5File[] files = tr.getFiles();
                if (files != null) {
                    for (final Html5File html5File : files) {
                        final String fileName = html5File.getFileName();

                        if (html5File.getFileSize() > 2 * 1024 * 1024) {
                        	Notification.show(Messages.getString("UploadView.notification3"), Notification.Type.ERROR_MESSAGE); //$NON-NLS-1$
                        } else {
                        	
                        	 final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                        	 
                        	 try {
                        		 
								 final FileOutputStream fos = new FileOutputStream (new File(FILE_UPLOAD_LOACTION + fileName));
                        	
	                             StreamVariable streamVariable = new StreamVariable() {
	                            	 
									@Override
									public OutputStream getOutputStream() {
										// TODO Auto-generated method stub
										return fos;
									}
	
									@Override
									public boolean listenProgress() {
										// TODO Auto-generated method stub
										return false;
									}
	
									@Override
									public void onProgress(
											StreamingProgressEvent event) {
										// TODO Auto-generated method stub
										
									}
	
									@Override
									public void streamingStarted(
											StreamingStartEvent event) {
										// TODO Auto-generated method stub
										
									}
	
									@Override
									public void streamingFinished(
											StreamingEndEvent event) {
										displayUploadedCreatives(filesTable);
										
									}
	
									@Override
									public void streamingFailed(
											StreamingErrorEvent event) {
										// TODO Auto-generated method stub
										
									}
	
									@Override
									public boolean isInterrupted() {
										// TODO Auto-generated method stub
										return false;
									}
	                             };
	                             
	                             html5File.setStreamVariable(streamVariable);

							 } catch (Exception e) {
								 // TODO Auto-generated catch block
								 e.printStackTrace();
							 }
                        }
                    }

                }  
            }
        });

        // Create uploads directory
        File uploads = new File(FILE_UPLOAD_LOACTION); 
        if (!uploads.exists() && !uploads.mkdir())
            layout.addComponent(new Label(Messages.getString("UploadView.notification4"))); //$NON-NLS-1$
        layout.addComponent(uploadWrapper);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		oldView = event.getOldView();		
	}
	
}
