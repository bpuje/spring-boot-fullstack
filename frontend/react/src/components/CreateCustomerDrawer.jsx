import {    Button,    Drawer,    DrawerBody,    DrawerCloseButton,    DrawerContent, DrawerFooter,    DrawerHeader,    DrawerOverlay, useDisclosure} from "@chakra-ui/react";import CreateCustomerForm from "./CreateCustomerForm.jsx";const AddIcon = () => "+";const CloseIcon = () => "x";const CreateCustomerDrawer = ({ fetchCustomers }) => {    const { isOpen, onOpen, onClose } = useDisclosure()    return <>        <Button            leftIcon={<AddIcon/>}            colorSchema={"teal"}            onClick={onOpen}        >            Create customer        </Button>        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>            <DrawerOverlay />            <DrawerContent>                <DrawerCloseButton />                <DrawerHeader>Create new account</DrawerHeader>                <DrawerBody>                    <CreateCustomerForm                        fetchCustomers={fetchCustomers}                    />                </DrawerBody>                <DrawerFooter>                    <Button                        leftIcon={<CloseIcon/>}                        colorSchema={"teal"}                        onClick={onClose}>                        Close                    </Button>                </DrawerFooter>            </DrawerContent>        </Drawer>        </>}export default CreateCustomerDrawer;